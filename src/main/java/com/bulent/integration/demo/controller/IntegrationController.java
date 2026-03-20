package com.bulent.integration.demo.controller;

import com.bulent.integration.demo.entity.IntegrationQueue;
import com.bulent.integration.demo.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integration")
@RequiredArgsConstructor
public class IntegrationController {

    private final IntegrationService service;

    @GetMapping("/health")
    public String health() {
        return "Integration Service Running";
    }

    @PostMapping("/queue")
    public IntegrationQueue queue(@RequestBody String payload) {
        return service.createQueueRecord(payload);
    }

    @GetMapping("/trigger")
    public String trigger() {
        service.fetchPendingRecords().forEach(service::processRecord);
        return "Manual trigger executed";
    }
}