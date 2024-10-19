package com.example.Coworker.ru.authenticationService.config.repository;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByUsername(String username);

    public Optional<User> findByVerificationCode(String code);
}
