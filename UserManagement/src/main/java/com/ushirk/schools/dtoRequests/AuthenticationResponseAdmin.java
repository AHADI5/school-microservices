package com.ushirk.schools.dtoRequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseAdmin {
    String firstName ;
    String lastName ;
    String email ;

    String token ;
    String refreshToken;

}
