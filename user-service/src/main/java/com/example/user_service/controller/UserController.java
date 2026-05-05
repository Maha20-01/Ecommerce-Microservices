package com.example.user_service.controller;

import com.example.common_library.dto.ApiResponse;
import com.example.user_service.dto.*;
import com.example.user_service.entity.User;
import com.example.user_service.security.JwtUtil;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 🔥 UPDATE CONSTRUCTOR
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ApiResponse<?> register(@Valid @RequestBody UserRequestDTO dto) {
        return userService.register(dto);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")

    public ApiResponse<?> getAllUsers() {
        return userService.getAllUsers();
    }

    // 🔥 ADD LOGIN API
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequestDTO dto) {
        return userService.login(dto);
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponse<>(true, "User deleted", null);
    }

    @GetMapping("/products")
    public ApiResponse<?> getProducts() {
        return userService.getProductsFromUserService();
    }
    @GetMapping("/email/{email}")
    public ApiResponse<?> getUserByEmail(@PathVariable String email) {

        User user = userService.findByEmail(email);

        if (user == null) {
            return new ApiResponse<>(false, "User not found", null);
        }

        return new ApiResponse<>(true, "User found", user);
    }
}