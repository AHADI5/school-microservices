package com.ushirk.schools.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ushirk.schools.model.StudentEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class StudentSerializer implements Serializer<StudentEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, StudentEvent data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing phase");
                return null ;
            }
            System.out.println("Serializing ...");
            return objectMapper.writeValueAsBytes(data);

        } catch (Exception e) {
            throw new SerializationException("Error when Serializing Student Event to byte");

        }
    }
}
