package com.example.Coworker.ru.mainService.common.repository;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.mainService.common.entity.Coworking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoworkingRepo extends JpaRepository<Coworking, Long> {

    public Coworking findByName(String name);
}
