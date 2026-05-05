package com.example.order_service.client;

import com.example.common_library.dto.ApiResponse;
import com.example.order_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ApiResponse<ProductDto> getProduct(@PathVariable Long id);

    @PutMapping("/products/reduce-stock/{id}")
    ApiResponse<?> reduceStock(@PathVariable Long id, @RequestParam int quantity);
}