package com.ushirk.schools.dtos;

import com.ushirk.schools.model.Student;

public record StudentResponse(
        long matricule
) {
    public static StudentResponse fromStudent(Student student) {
        return new StudentResponse(
                student.getMatricule()
        );
    }
}

