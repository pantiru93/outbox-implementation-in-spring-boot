package dev.rolletech.outbox.repository;

import dev.rolletech.outbox.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findByStatus(String status);
} 