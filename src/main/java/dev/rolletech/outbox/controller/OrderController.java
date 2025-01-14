package dev.rolletech.outbox.controller;

import dev.rolletech.outbox.dto.OrderRequest;
import dev.rolletech.outbox.model.Order;
import dev.rolletech.outbox.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }
} 