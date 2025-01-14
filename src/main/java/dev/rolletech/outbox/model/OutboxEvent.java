package dev.rolletech.outbox.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "outbox")
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String eventType;
    private String payload;
    private String partitionKey;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
} 