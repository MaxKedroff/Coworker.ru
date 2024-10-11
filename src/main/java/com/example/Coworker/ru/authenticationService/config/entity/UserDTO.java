package com.example.Coworker.ru.authenticationService.config.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
    @Email(message = "Invalid email address")
    @Pattern(regexp = "@.*@urfu\\.me$", message = "Email must end with @urfu.me")
    private String email;

    private String password;

    public @Email(message = "Invalid email address") @Pattern(regexp = "@.*@urfu\\.me$", message = "Email must end with @urfu.me") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email address") @Pattern(regexp = "@.*@urfu\\.me$", message = "Email must end with @urfu.me") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
