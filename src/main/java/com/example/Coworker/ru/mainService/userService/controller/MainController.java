package com.example.Coworker.ru.mainService.userService.controller;

import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.userService.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('student')")
@RequestMapping("api/main/")
public class MainController {

    @Autowired
    private MainPageService mainPageService;


    @GetMapping
    public ResponseEntity<List<Coworking>> getAllCoworkings(){
        return ResponseEntity.status(200).body(mainPageService.getAllCoworkings());
    }

    @GetMapping("/{coworking}")
    public ResponseEntity<Coworking> getCoworking(@RequestParam String coworkingName){
        return ResponseEntity.status(200).body(mainPageService.getCoworkingByName(coworkingName));
    }


}
