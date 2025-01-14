package dev.rolletech.outbox.repository;

import dev.rolletech.outbox.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
} 