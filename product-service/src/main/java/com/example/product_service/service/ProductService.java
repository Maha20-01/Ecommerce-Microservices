package com.example.product_service.service;

import com.example.common_library.dto.ApiResponse;
import com.example.product_service.entity.Product;

public interface ProductService {

    ApiResponse<?> create(Product product);

    ApiResponse<?> getAll();
    ApiResponse<?> getById(Long id);
    ApiResponse<?> reduceStock(Long id, int quantity);
}