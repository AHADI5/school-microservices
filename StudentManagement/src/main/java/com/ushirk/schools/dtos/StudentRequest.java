package com.ushirk.schools.dtos;

public record StudentRequest(
        Long matricule,
        String firstName,
        String lastName,
        String email,
        long promotionID
) {
}
