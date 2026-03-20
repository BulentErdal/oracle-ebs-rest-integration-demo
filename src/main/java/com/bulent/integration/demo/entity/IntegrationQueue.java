package com.bulent.integration.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class IntegrationQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payload;

    private String status; // NEW, PROCESSING, DONE, FAILED

    private int retryCount;

    private LocalDateTime createdDate;

    private LocalDateTime lastAttemptDate;
}