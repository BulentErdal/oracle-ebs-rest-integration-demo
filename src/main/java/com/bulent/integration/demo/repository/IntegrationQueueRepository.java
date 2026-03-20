package com.bulent.integration.demo.repository;

import com.bulent.integration.demo.entity.IntegrationQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntegrationQueueRepository extends JpaRepository<IntegrationQueue, Long> {

    List<IntegrationQueue> findTop100ByStatusOrderByIdAsc(String status);
}