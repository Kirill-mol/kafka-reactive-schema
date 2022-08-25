package ru.learn.reactive.kafka.service;

import notification.NotificationAvro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.learn.reactive.kafka.domain.Notification;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class ReactiveProducerService {

    private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);
    private final ReactiveKafkaProducerTemplate<UUID, NotificationAvro> reactiveKafkaProducerTemplate;

    @Value("${notification.topic}")
    private String topic;

    public ReactiveProducerService(ReactiveKafkaProducerTemplate<UUID, NotificationAvro> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    @PostConstruct
    public void send() {
        Flux.range(0, 25)
                .delayElements(Duration.of(3, SECONDS))
                .map(number -> new NotificationAvro(UUID.randomUUID().toString(), String.valueOf(number)))
                .flatMap(notification -> reactiveKafkaProducerTemplate.send(topic, UUID.fromString(notification.getId()), notification))
                .doOnNext(senderResult -> log.info("sent offset : {}", senderResult.recordMetadata().offset()))
                .subscribe();
    }
}