package com.bulent.integration.demo.service;

import com.bulent.integration.demo.entity.IntegrationQueue;
import com.bulent.integration.demo.repository.IntegrationQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final IntegrationQueueRepository repository;

    public List<IntegrationQueue> fetchPendingRecords() {
        return repository.findTop100ByStatusOrderByIdAsc("NEW");
    }

    public IntegrationQueue createQueueRecord(String payload) {
        IntegrationQueue q = new IntegrationQueue();
        q.setPayload(payload);
        q.setStatus("NEW");
        q.setRetryCount(0);
        q.setCreatedDate(LocalDateTime.now());
        return repository.save(q);
    }

    public void processRecord(IntegrationQueue q) {
        try {
            q.setStatus("PROCESSING");
            q.setLastAttemptDate(LocalDateTime.now());
            repository.save(q);

            simulateExternalCall(q);

            q.setStatus("DONE");
            q.setErrorMessage(null);
            repository.save(q);

        } catch (Exception e) {
            q.setRetryCount(q.getRetryCount() + 1);
            q.setStatus("FAILED");
            q.setErrorMessage(e.getMessage());
            q.setLastAttemptDate(LocalDateTime.now());
            repository.save(q);
        }
    }

    public void retryFailedRecords() {
        List<IntegrationQueue> failedList = repository.findTop100ByStatusOrderByIdAsc("FAILED");
        for (IntegrationQueue q : failedList) {
            if (q.getRetryCount() < 3) {
                q.setStatus("NEW");
                repository.save(q);
            }
        }
    }

    private void simulateExternalCall(IntegrationQueue q) {
        if (q.getPayload() != null && q.getPayload().toLowerCase().contains("fail")) {
            throw new RuntimeException("Simulated external system failure");
        }
    }
}