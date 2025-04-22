package com.ushirk.schools.dtoRequests;



import com.ushirk.schools.model.Role;

import java.util.Date;

public record UserResponse(
        int userID ,
        String firstName ,
        String lastName ,
        String email ,
        boolean enabled ,
        boolean isValid ,
        boolean isBlocked ,
        Role role,
        Date createdAt
) {

}
