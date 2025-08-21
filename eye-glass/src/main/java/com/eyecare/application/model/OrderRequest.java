package com.eyecare.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class OrderRequest {
    private String customerId;
    private String frameType;
    private String lensType;
    private Instant orderTimestamp;
}
