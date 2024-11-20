package com.example.Coworker.ru.mainService.adminService;

import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.common.entity.CoworkingRequest;
import com.example.Coworker.ru.mainService.common.repository.CoworkingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    CoworkingRepo coworkingRepo;

    public void AddCoworking(CoworkingRequest coworkingRequest){
        Coworking coworking = new Coworking();
        coworking.setName(coworkingRequest.getName());
        coworking.setDescription(coworkingRequest.getDescription());
        coworking.setAddress(coworkingRequest.getAddress());
        coworkingRepo.save(coworking);
    }
}
