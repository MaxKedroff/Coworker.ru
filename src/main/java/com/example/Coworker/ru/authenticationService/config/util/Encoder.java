package com.example.Coworker.ru.authenticationService.config.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder implements PasswordEncoderService{
    @Override
    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
