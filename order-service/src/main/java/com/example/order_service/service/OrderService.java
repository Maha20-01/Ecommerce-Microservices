package com.example.order_service.service;

import com.example.common_library.dto.ApiResponse;
import com.example.order_service.client.ProductClient;
import com.example.order_service.client.UserClient;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponseDTO;
import com.example.order_service.dto.ProductDto;
import com.example.order_service.entity.Order;
import com.example.order_service.exception.BusinessException;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;

    public ApiResponse<OrderResponseDTO> placeOrder(OrderRequest request) {

        // ✅ 1. Validate User
        ApiResponse<?> userResponse =
                userClient.getUserByEmail(request.getUserEmail());

        if (userResponse.getData() == null) {
            throw new RuntimeException("User not found");
        }

        // ✅ 2. Validate Product
        ApiResponse<ProductDto> productResponse =
                productClient.getProduct(request.getProductId());

        ProductDto product = productResponse.getData();

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // ✅ 3. Check Stock
        if (product.getStock() < request.getQuantity()) {
            throw new BusinessException("Insufficient stock");
        }
        // ✅ 4. Reduce Stock
        productClient.reduceStock(
                request.getProductId(),
                request.getQuantity()
        );

        // ✅ 5. Save Order
        Order order = new Order();
        order.setUserEmail(request.getUserEmail());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());

        Order savedOrder = orderRepository.save(order);

        // ✅ 6. Calculate total price
        double totalPrice = product.getPrice() * request.getQuantity();

        // ✅ 7. Build response DTO
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(savedOrder.getId());
        response.setUserEmail(savedOrder.getUserEmail());
        response.setProductId(savedOrder.getProductId());
        response.setQuantity(savedOrder.getQuantity());
        response.setTotalPrice(totalPrice);

        return new ApiResponse<>(
                true,
                "Order placed successfully",
                response
        );
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}