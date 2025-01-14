package dev.rolletech.outbox.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderRequest {
    private String customerName;
    private BigDecimal totalAmount;
} 