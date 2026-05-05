package com.example.user_service.dto;

import jakarta.validation.constraints.*;

public class UserRequestDTO {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phone;
    private String password;
    // getters & setters

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}