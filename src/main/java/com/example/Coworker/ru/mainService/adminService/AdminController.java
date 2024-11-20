package com.example.Coworker.ru.mainService.adminService;


import com.example.Coworker.ru.mainService.common.entity.CoworkingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/main/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<String> AddCoworking(@RequestBody CoworkingRequest request){
        adminService.AddCoworking(request);
        return ResponseEntity.ok("коворкинг успешно добавлен");
    }
}
