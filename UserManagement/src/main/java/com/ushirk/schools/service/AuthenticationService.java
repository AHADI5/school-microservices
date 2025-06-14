package com.ushirk.schools.service;


import com.ushirk.schools.config.JwtService;
import com.ushirk.schools.dtoRequests.*;
import com.ushirk.schools.model.Role;
import com.ushirk.schools.model.Users;
import com.ushirk.schools.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public record AuthenticationService(
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        UserRepository userRepository
) {
    public void register(RegisterRequest request, Role role) {
        Users user = Users.builder()
                .firstName(request.getFirstName())
                .enabled(true)
                .lastName(request.getLastName())
                .email(request.getEmail())
                .schoolID(request.getSchoolID())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .createdAt(new Date())
                .build();
        userRepository.save(user);
//        var jwToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwToken)
//                .build();

    }

    public AuthenticationResponseAdmin registerAdmin(RegisterRequest request, Role role) {
        //Checking whether the user Exists
        Optional<Users> existingUser = userRepository.findByEmail(request.getEmail());

        Users user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .schoolID(request.getSchoolID())
                .email(request.getEmail())
                .enabled(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        Users savedUser = userRepository.save(user);
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponseAdmin.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .token(jwToken)
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("userNot found"));
        user.setDeviceUniqueKey(request.getDeviceKey());
        //save the user after updating the uniqueDeviceToken
        Users updatedUser = userRepository.save(user);
        //Publish the user as Recepient
       // messageController.publishUniqueDeviceKey(updatedUser.getEmail() , updatedUser.getDeviceUniqueKey());
        var jwToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);


        return AuthenticationResponse.builder()
                .token(jwToken)
                .refreshToken(refreshToken)
                .schoolID(user.getSchoolID())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken.getToken());
        Users user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshToken.getToken(), user)) {
            var jwToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwToken)
                    .build();
        }
        return null;
    }

    //validate token

    public Boolean validateToken(String token) {
        String userEmail = jwtService().extractUsername(token);
        Users user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        return jwtService().isTokenValid(token , user);
    }


    //Logic to activate or disable a user account
    public ResponseEntity<Boolean> disableUser (String userName)  {
        Users user  = userRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        if (user.isEnabled()) {
            user.setEnabled(false);
            //Register Operations

            userRepository.save(user);
            return ResponseEntity.ok(user.isEnabled());
        }

        return null;
    }

    public ResponseEntity<Boolean> enableUser (String userName) {
        Users user  = userRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        if (!user.isEnabled()) {
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok(user.isEnabled());
        }
        return null;
    }



    public Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error
        }
    }

    public boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public List<UserResponse> simpleUserResponse  (List<Users>  users) {
        List<UserResponse> responses = new ArrayList<>();
        for (Users user  : users ) {
            UserResponse userResponse = new UserResponse(
                    user.getUserID(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.isEnabled() ,
                    user.isAccountNonExpired(),
                    user.isAccountNonLocked(),
                    user.getRole(),
                    user.getCreatedAt()
            );
            responses.add(userResponse);


        }

        return responses;

    }

    public String getUniDeviceKeyByUserName(DeviceRequest request) {
        //load user
        Users user  = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getDeviceUniqueKey();
    }



}
