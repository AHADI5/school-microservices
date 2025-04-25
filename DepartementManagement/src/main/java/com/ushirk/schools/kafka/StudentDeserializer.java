package com.ushirk.schools.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ushirk.schools.model.Student;
import com.ushirk.schools.model.StudentEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class StudentDeserializer implements Deserializer<StudentEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public StudentEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return  null ;
            }
            System.out.println("Deserializing ...");
            return  objectMapper.readValue(new String(data , StandardCharsets.UTF_8), StudentEvent.class);

        } catch (Exception e) {
            throw  new SerializationException("Error When deserializing byte[] to StudentEvent");
        }

    }

}
