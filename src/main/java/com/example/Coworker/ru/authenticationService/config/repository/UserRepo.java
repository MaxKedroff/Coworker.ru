package com.example.Coworker.ru.authenticationService.config.repository;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
