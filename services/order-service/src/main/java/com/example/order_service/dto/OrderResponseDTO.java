package com.example.order_service.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {

    private Long id;
    private String userEmail;
    private Long productId;
    private int quantity;
    private double totalPrice;
}