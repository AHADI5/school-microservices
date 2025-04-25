package com.ushirk.schools.dtos;

import com.ushirk.schools.model.Grade;
import com.ushirk.schools.model.Student;

import java.util.List;

public record GradeResponse(
        long gradeID,
        String department,
        String gradeName,
        List<StudentResponse> studentList
) {
    public static GradeResponse fromGrade(Grade grade) {
        return new GradeResponse(
                grade.getGradeID(),
                grade.getDepartment() != null ? grade.getDepartment().getDepartmentName() : null,
                grade.getName(),
                grade.getStudents() != null
                        ? grade.getStudents().stream()
                        .map(StudentResponse::fromStudent)
                        .toList()
                        : List.of()
        );

    }
}

