# Oracle REST Integration Scheduler Demo

## Overview

This project demonstrates a simplified enterprise integration architecture built with Java Spring Boot and Oracle-style processing patterns.

It simulates a production-oriented middleware service that:

- receives integration payloads
- stores them in a queue-style table
- processes them asynchronously with scheduler workers
- retries failed transactions
- keeps transaction-level state for monitoring and traceability

The design reflects real-world enterprise integration patterns used in high-volume Oracle environments.

---

## Business Purpose

Enterprise systems often require reliable synchronization with external platforms through REST APIs.

In these scenarios, direct synchronous processing is usually not enough because of:

- network instability
- temporary external system failures
- high transaction volume
- retry requirements
- monitoring and audit needs

This demo shows how a middleware layer can provide controlled, reliable, and scalable processing using scheduler-driven orchestration.

---

## Architecture

### Main Components

- **Controller Layer**
  - exposes REST endpoints for health checks, queue insertion, and manual triggering

- **Service Layer**
  - contains integration orchestration logic
  - handles processing, retry preparation, and failure updates

- **Repository Layer**
  - provides queue table access using Spring Data JPA

- **Scheduler Layer**
  - periodically scans pending records
  - retries failed transactions based on retry rules

- **Config Layer**
  - provides REST client bean
  - prepares scheduler thread pool configuration

- **Queue Entity**
  - represents transaction-based processing state
  - stores payload, status, retry count, timestamps, and error messages

---

## Processing Flow

1. Incoming payload is inserted into the integration queue
2. New records are stored with status `NEW`
3. Scheduler scans pending records at fixed intervals
4. Each record is moved to `PROCESSING`
5. External call is simulated
6. Successful records are marked as `DONE`
7. Failed records are marked as `FAILED`
8. Retry scheduler resets eligible failed records back to `NEW`
9. Processing continues until completion or retry limit is reached

---

## Retry Logic

The retry mechanism is designed to simulate real production integration behavior:

- failed transactions are not lost
- retry count is tracked
- failed records can be re-queued automatically
- retry attempts are limited to avoid infinite loops
- last attempt timestamp and error reason are preserved

This pattern is commonly used in enterprise Oracle integrations where reliability and recoverability are critical.

---

## Queue Model

The queue entity contains the following core attributes:

- `id`
- `payload`
- `status` (`NEW`, `PROCESSING`, `DONE`, `FAILED`)
- `retryCount`
- `createdDate`
- `lastAttemptDate`
- `errorMessage`

This structure allows transaction-level monitoring and operational visibility.

---

## Exposed Endpoints

### Health Check
`GET /integration/health`

Returns service health status.

### Queue New Payload
`POST /integration/queue`

Creates a new integration queue record.

### Manual Trigger
`GET /integration/trigger`

Triggers manual processing of currently pending records.

---

## Technical Patterns Demonstrated

- Scheduler-driven background processing
- Queue table architecture
- Retry orchestration
- Failure recovery design
- Transaction state management
- REST middleware design
- Controller / Service / Repository separation
- Configurable thread-pool scheduling

---

## Example Enterprise Use Cases

This architecture pattern is suitable for:

- consent synchronization
- customer data exchange
- account opening workflows
- regulatory integration pipelines
- asynchronous notification processing
- external platform reconciliation

---

## Reliability & Scalability Notes

This demo is intentionally simplified, but the same pattern can be extended with:

- Oracle database instead of H2
- real REST client integration
- configurable retry backoff
- dead-letter queue design
- distributed worker scaling
- batch size tuning
- audit and monitoring dashboards

---

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Scheduler
- REST Architecture Patterns

---

## Why This Demo Matters

This repository is not just a basic Spring Boot sample.

It represents a compact version of a real enterprise integration architecture pattern used in production systems where:

- reliability matters
- retry design matters
- transaction traceability matters
- asynchronous orchestration matters

It showcases practical middleware thinking beyond simple CRUD development.


## Architecture Diagram

```text
Client / Source System
        |
        v
 REST Controller
        |
        v
 Integration Service
        |
        v
 Integration Queue Table
        |
        v
 Scheduler Workers
   |             |
   v             v
Process New   Retry Failed
   |             |
   +-------> External System
                 |
                 v
          Status / Logging Update
