package com.example.order_service.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String userEmail;
    private Long productId;
    private int quantity;
}