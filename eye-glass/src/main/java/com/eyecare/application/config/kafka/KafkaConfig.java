package com.eyecare.application.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic eyeglassOrdersTopic() {
        return TopicBuilder
                .name("eyeglass-orders")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
