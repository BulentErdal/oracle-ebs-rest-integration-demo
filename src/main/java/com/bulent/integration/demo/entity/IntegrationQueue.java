package com.bulent.integration.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "integration_queue")
public class IntegrationQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String payload;

    @Column(length = 20)
    private String status; // NEW, PROCESSING, DONE, FAILED

    private int retryCount;

    private LocalDateTime createdDate;

    private LocalDateTime lastAttemptDate;

    @Column(length = 1000)
    private String errorMessage;
}