package com.eyecare.application.event.handler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private String frameType;
    private String lensType;
    private Instant orderTimestamp;

}
