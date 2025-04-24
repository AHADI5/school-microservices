package com.ushirk.schools.controllers.messages;

import com.ushirk.schools.model.Student;
import com.ushirk.schools.model.StudentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageController {
    final KafkaTemplate<String, StudentEvent> kafkaTemplate;

    public MessageController(KafkaTemplate<String, StudentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStudentEvent(Student student) {
        StudentEvent studentEvent = new StudentEvent();
        studentEvent.setMatricule(student.getMatricule());
        studentEvent.setGradeID(student.getPromotionID());

        kafkaTemplate.send("student-created", studentEvent);
        log.info("Published student event {}", studentEvent);
    }


}
