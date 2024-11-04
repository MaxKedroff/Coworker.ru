package com.example.Coworker.ru.mainService.userService.service;

import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.common.repository.CoworkingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
