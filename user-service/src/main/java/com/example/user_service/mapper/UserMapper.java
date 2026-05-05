package com.example.user_service.mapper;

import com.example.user_service.dto.UserRequestDTO;
import com.example.user_service.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return user;
    }
}