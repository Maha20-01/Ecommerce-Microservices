package com.example.user_service.service;

import com.example.common_library.dto.ApiResponse;
import com.example.user_service.client.ProductClient;
import com.example.user_service.dto.*;
import com.example.user_service.entity.User;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final ProductClient productClient;

    // ✅ Constructor Injection
    public UserServiceImpl(UserRepository repository,
                           BCryptPasswordEncoder encoder,
                           JwtUtil jwtUtil,
                           ProductClient productClient) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.productClient = productClient;
    }

    // ✅ FIXED (ONLY ONE METHOD)
    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public ApiResponse<?> register(UserRequestDTO dto) {

        User user = UserMapper.toEntity(dto);

        user.setPassword(encoder.encode(dto.getPassword())); // 🔐 hash password
        user.setRole("USER");

        repository.save(user);

        return new ApiResponse<>(true, "User created", null);
    }

    @Override
    public ApiResponse<?> login(LoginRequestDTO dto) {

        User user = repository.findByEmail(dto.getEmail())
                .orElse(null);

        if (user == null) {
            return new ApiResponse<>(false, "User not found", null);
        }

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            return new ApiResponse<>(false, "Invalid password", null);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new ApiResponse<>(true, "Login success", token);
    }

    @Override
    public ApiResponse<?> getAllUsers() {
        return new ApiResponse<>(true, "Users fetched", repository.findAll());
    }

    @Override
    public ApiResponse<?> getProductsFromUserService() {
        return productClient.getAllProducts();
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}