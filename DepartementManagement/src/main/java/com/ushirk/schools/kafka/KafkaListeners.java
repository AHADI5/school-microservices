package com.ushirk.schools.kafka;
import com.ushirk.schools.model.Student;
import com.ushirk.schools.model.StudentEvent;
import com.ushirk.schools.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record KafkaListeners(
        StudentService studentService
) {
    private static final String STUDENT_TOPIC = "student-created";

    @KafkaListener(
            topics = STUDENT_TOPIC  ,
            groupId = "student" ,
            containerFactory = "kafkaListenerContainerFactoryStudent"
    )
    void listener(StudentEvent studentEvent) {
        log.info("StudentEvent received in Department Service: {}", studentEvent);
        studentService.registerStudent(studentEvent.getMatricule()  , studentEvent.getGradeID()) ;

    }



}
