package com.example.Coworker.ru.mainService.adminService;


import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.common.entity.CoworkingRequest;
import com.example.Coworker.ru.mainService.userService.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/main/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<String> AddCoworking(@RequestBody CoworkingRequest request){
        adminService.AddCoworking(request);
        return ResponseEntity.ok("коворкинг успешно добавлен");
    }
}
