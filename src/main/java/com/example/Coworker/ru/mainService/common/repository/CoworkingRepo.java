package com.example.Coworker.ru.mainService.common.repository;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.mainService.common.entity.Coworking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoworkingRepo extends JpaRepository<Coworking, UUID> {

    public Coworking findByName(String name);
}
