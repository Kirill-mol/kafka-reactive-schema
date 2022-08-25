package ru.learn.reactive.kafka.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.UUID;

@JsonRootName("FakeConsumer")
public class Notification {

    @JsonProperty("id")
    private UUID id;
    private String message;

    public Notification(@JsonProperty("id") UUID id, @JsonProperty("message") String message) {
        this.id = id;
        this.message = message;
    }

    public Notification(String message) {
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
