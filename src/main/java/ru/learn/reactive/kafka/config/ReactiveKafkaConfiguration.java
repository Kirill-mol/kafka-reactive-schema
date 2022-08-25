package ru.learn.reactive.kafka.config;

import notification.NotificationAvro;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Configuration
public class ReactiveKafkaConfiguration {

    @Bean
    public ReceiverOptions<UUID, NotificationAvro> kafkaReceiverOptions(
            @Value("${notification.topic}") String topic,
            KafkaProperties kafkaProperties
    ) {
        ReceiverOptions<UUID, NotificationAvro> basicReceiverOptions =
                ReceiverOptions.create(kafkaProperties.buildConsumerProperties());

        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<UUID, NotificationAvro> reactiveKafkaConsumerTemplate(
            ReceiverOptions<UUID, NotificationAvro> kafkaReceiverOptions
    ) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }

    @Bean
    public ReactiveKafkaProducerTemplate<UUID, NotificationAvro> reactiveKafkaProducerTemplate(
            KafkaProperties properties
    ) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }
}
