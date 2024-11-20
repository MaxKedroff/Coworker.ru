package com.example.Coworker.ru.mainService.userService.service;

import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.common.repository.CoworkingRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MainPageService {

    @Autowired
    private CoworkingRepo coworkingRepo;

    public List<Coworking> getAllCoworkings(){
        return coworkingRepo.findAll();
    }

    public Coworking getCoworkingByName(String name){
        return coworkingRepo.findByName(name);
    }

    public Coworking getCoworkingById(UUID id) {
        return coworkingRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Коворкинг с id " + id + " не найден"));
    }

}
