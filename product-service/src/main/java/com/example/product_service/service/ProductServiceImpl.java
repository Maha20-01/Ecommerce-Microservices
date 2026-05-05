package com.example.product_service.service;

import com.example.common_library.dto.ApiResponse;
import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApiResponse<?> create(Product product) {
        repository.save(product);
        return new ApiResponse<>(true, "Product created", product);
    }

    @Override
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(true, "Products fetched", repository.findAll());
    }

    // ✅ ADD THIS (GET PRODUCT BY ID)
    @Override
    public ApiResponse<?> getById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ApiResponse<>(true, "Product found", product);
    }

    // ✅ ADD THIS (REDUCE STOCK)
    @Override
    public ApiResponse<?> reduceStock(Long id, int quantity) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);
        repository.save(product);

        return new ApiResponse<>(true, "Stock updated", product);
    }
}