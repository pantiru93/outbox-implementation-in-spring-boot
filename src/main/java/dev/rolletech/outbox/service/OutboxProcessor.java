package dev.rolletech.outbox.service;

import dev.rolletech.outbox.model.OutboxEvent;
import dev.rolletech.outbox.repository.OutboxRepository;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OutboxProcessor {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxProcessor(OutboxRepository outboxRepository,
                         KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000)
    @SchedulerLock(name = "outboxProcessor", lockAtLeastFor = "PT4S", lockAtMostFor = "PT30S")
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> events = outboxRepository.findByStatus("NOT_STARTED");
        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send("orders", event.getPartitionKey(), event.getPayload()).get();
                event.setStatus("SENT");
                event.setProcessedAt(LocalDateTime.now());
            } catch (Exception e) {
                event.setStatus("FAILED");
                log.error("Failed to publish event to Kafka", e);
            } finally {
                outboxRepository.save(event);
            }
        }
    }
} 