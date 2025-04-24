package com.ushirk.schools.kafka;

import com.ushirk.schools.model.StudentEvent;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {
    public Map<String , Object> producerConfigStudentEvent() {
        HashMap<String , Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StudentSerializer.class);
        return props;

    }

    @Bean
    public ProducerFactory<String , StudentEvent> producerFactoryClassWork() {
        return  new DefaultKafkaProducerFactory<>(producerConfigStudentEvent());
    }
    @Bean
    public KafkaTemplate<String , StudentEvent> kafkaTemplateClassWork() {
        return  new KafkaTemplate<>(producerFactoryClassWork());
    }

}
