package com.ushirk.schools.services;

import com.ushirk.schools.dtos.StudentResponse;
import com.ushirk.schools.model.Grade;
import com.ushirk.schools.model.Student;
import com.ushirk.schools.repository.GradeRepository;
import com.ushirk.schools.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record StudentService(
        StudentRepository  studentRepository ,
        GradeRepository gradeRepository
) {
    public Student registerStudent(long matricule , long gradeId) {
        Optional<Grade> grade   = gradeRepository.findById(gradeId);
        Student student = Student.builder()
                .matricule(matricule)
                .grade(grade.get())
                .build();

        return studentRepository.save(student);
    }
}
