package com.ushirk.schools.dtos;

import com.ushirk.schools.model.Department;

import java.util.List;

public record DepartmentResponse(
        long departmentId,
        String departmentName,
        List<GradeResponse> gradeResponses
) {
    public static DepartmentResponse fromDepartment(Department department) {
        return new DepartmentResponse(
                department.getDepartmentID(),
                department.getDepartmentName(),
                department.getGrades() != null
                        ? department.getGrades().stream()
                        .map(GradeResponse::fromGrade)
                        .toList()
                        : List.of()
        );
    }

}
