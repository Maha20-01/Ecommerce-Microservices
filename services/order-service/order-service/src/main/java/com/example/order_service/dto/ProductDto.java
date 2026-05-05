package com.example.order_service.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private int stock;
    private double price;
}