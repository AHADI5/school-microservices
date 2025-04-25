package com.ushirk.schools.controllers;


import com.ushirk.schools.config.JwtService;
import com.ushirk.schools.dtoRequests.*;
import com.ushirk.schools.model.Role;
import com.ushirk.schools.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/auth")
public record AuthenticationController(
        AuthenticationService authenticationService,
        JwtService jwtService

) {
//    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @PostMapping("/admin")
    public AuthenticationResponseAdmin register(
            @RequestBody RegisterRequest request
    ) {

        return authenticationService.registerAdmin(request, Role.ADMIN);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));

    }
    @PostMapping("/validate")

    public Boolean isTokenValid(@RequestParam("token") String token) {
        log.info("checking current token :" + token);
        return authenticationService.validateToken(token);
    }
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        jwtService.validateToken(token);
        return  "the token " + token + "is valid" ;
    }

    @PostMapping("/user")
    public  void registerUser(@RequestBody RegisterRequest user) {
        authenticationService.register(user , Role.ADMIN);
    }


}
