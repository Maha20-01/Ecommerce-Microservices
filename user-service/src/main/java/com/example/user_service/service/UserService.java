package com.example.user_service.service;

import com.example.common_library.dto.ApiResponse;
import com.example.user_service.dto.LoginRequestDTO;
import com.example.user_service.dto.UserRequestDTO;
import com.example.user_service.entity.User;

public interface UserService {

    ApiResponse<?> register(UserRequestDTO dto);

    ApiResponse<?> getAllUsers();

    ApiResponse<?> login(LoginRequestDTO dto);

    ApiResponse<?> getProductsFromUserService();
    // 🔥 ADD THIS
    User findByEmail(String email);
    void deleteUser(Long id);

}