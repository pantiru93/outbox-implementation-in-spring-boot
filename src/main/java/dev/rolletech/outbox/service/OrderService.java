package dev.rolletech.outbox.service;

import dev.rolletech.outbox.dto.OrderRequest;
import dev.rolletech.outbox.model.Order;
import dev.rolletech.outbox.model.OutboxEvent;
import dev.rolletech.outbox.repository.OrderRepository;
import dev.rolletech.outbox.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) throws Exception {
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("CREATED");
        
        order = orderRepository.save(order);

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setEventType("ORDER_CREATED");
        outboxEvent.setPayload(objectMapper.writeValueAsString(order));
        outboxEvent.setPartitionKey(order.getId().toString());
        outboxEvent.setStatus("NOT_STARTED");
        outboxEvent.setCreatedAt(LocalDateTime.now());
        
        outboxRepository.save(outboxEvent);
        
        return order;
    }
} 