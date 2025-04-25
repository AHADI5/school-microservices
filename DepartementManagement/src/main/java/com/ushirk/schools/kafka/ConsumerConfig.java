package com.ushirk.schools.kafka;

import com.ushirk.schools.model.StudentEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class ConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private  String boostrapServers;


    private ConsumerFactory<String, StudentEvent> consumerFactoryStudent() {
        Map<String, Object> propsStudent = new HashMap<>();
        propsStudent.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        propsStudent.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "Student");
        propsStudent.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use ErrorHandlingDeserializer for value deserializer
        ErrorHandlingDeserializer<StudentEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new StudentDeserializer());

        return new DefaultKafkaConsumerFactory<>(propsStudent, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,StudentEvent> kafkaListenerContainerFactoryStudent(){
        ConcurrentKafkaListenerContainerFactory<String , StudentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryStudent());
        return factory;
    }
}
