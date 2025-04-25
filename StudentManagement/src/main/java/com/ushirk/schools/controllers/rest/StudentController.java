package com.ushirk.schools.controllers.rest;

import com.ushirk.schools.dtos.StudentRequest;
import com.ushirk.schools.dtos.StudentResponse;
import com.ushirk.schools.model.Student;
import com.ushirk.schools.services.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public record StudentController(
        StudentService studentService
) {

    @PostMapping("/student")
    public StudentResponse createStudent(@RequestBody StudentRequest student) {
        return StudentResponse.toStudentResponse(studentService.registerStudent(student)) ;
    }
}
