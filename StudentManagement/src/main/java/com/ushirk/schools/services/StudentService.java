package com.ushirk.schools.services;

import com.ushirk.schools.controllers.messages.MessageController;
import com.ushirk.schools.dtos.StudentRequest;
import com.ushirk.schools.model.Student;
import com.ushirk.schools.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public record StudentService(
        StudentRepository  studentRepository  ,
        MessageController messageController
) {
    public Student registerStudent(StudentRequest studentRequest) {
        Student student  = Student.builder()
                .matricule(studentRequest.matricule())
                .firstName(studentRequest.firstName())
                .lastName(studentRequest.lastName())
                .email(studentRequest.email())
                .promotionID(studentRequest.promotionID())
                .build();
        Student studentCreated  = studentRepository.save(student);
        messageController.publishStudentEvent(studentCreated);

        return studentCreated;
    }
}
