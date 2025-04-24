package com.ushirk.schools.dtos;

import com.ushirk.schools.model.Student;

public record StudentResponse(
        Long studentId,
        Long matricule,
        String firstName,
        String lastName,
        String email,
        long promotionID
) {

    public static StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(student.getStudentID(), student.getMatricule(), student.getFirstName(), student.getLastName(),student.getEmail(), student.getPromotionID());
    }
}
