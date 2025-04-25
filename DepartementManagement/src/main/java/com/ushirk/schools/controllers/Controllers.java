package com.ushirk.schools.controllers;

import com.ushirk.schools.dtos.*;
import com.ushirk.schools.model.Student;
import com.ushirk.schools.services.DepartementService;
import com.ushirk.schools.services.GradeService;
import com.ushirk.schools.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/department")
public record Controllers(
        DepartementService departementService  ,
        GradeService gradeService  ,
        StudentService studentService
) {
    @PostMapping("/department/")
    public DepartmentResponse regiterDepartmentResponse(@RequestBody DepartmentRequest departmentRequest) {
        return DepartmentResponse.fromDepartment(departementService.registerDepartment(departmentRequest));
    }
    @PostMapping("/grade/")
    public GradeResponse registerGrade(@RequestBody GradeRequest gradeRequest) {
        return GradeResponse.fromGrade(gradeService.registerGrade(gradeRequest));
    }

    @GetMapping("/{gradeID}")
    public List<StudentResponse> getGradeStudent(@PathVariable long gradeID) {
        List<Student> students = gradeService.getStudents(gradeID);
        List<StudentResponse> studentResponses = new ArrayList<>();

        for (Student student : students) {
            StudentResponse studentResponse = StudentResponse.fromStudent(student) ;
            studentResponses.add(studentResponse);
        }
        return studentResponses;

    }
}
