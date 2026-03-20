package com.bulent.integration.demo.scheduler;

import com.bulent.integration.demo.entity.IntegrationQueue;
import com.bulent.integration.demo.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IntegrationScheduler {

    private final IntegrationService service;

    @Scheduled(fixedDelay = 10000)
    public void processQueue() {
        List<IntegrationQueue> list = service.fetchPendingRecords();
        for (IntegrationQueue q : list) {
            service.processRecord(q);
        }
    }

    @Scheduled(fixedDelay = 30000)
    public void retryFailedQueue() {
        service.retryFailedRecords();
    }
}