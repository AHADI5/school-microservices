package com.ushirk.schools.services;

import com.ushirk.schools.dtos.GradeRequest;
import com.ushirk.schools.model.Department;
import com.ushirk.schools.model.Grade;
import com.ushirk.schools.model.Student;
import com.ushirk.schools.repository.DepartmentRepository;
import com.ushirk.schools.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record GradeService(
        GradeRepository gradeRepository   ,
        DepartmentRepository departmentRepository
) {
    public Grade registerGrade (GradeRequest gradeRequest) {
        Optional<Department> department  = departmentRepository.findById(gradeRequest.departmentID());
        Grade grade = Grade.builder()
                .name(gradeRequest.gradeName() + "/" + department.get().getDepartmentName())
                .department(department.get())
                .build();

        return  gradeRepository.save(grade);
    }

    public List<Student> getStudents(long gradeID) {
        Grade grade = gradeRepository.findById(gradeID).get();

        return grade.getStudents();


    }
}
