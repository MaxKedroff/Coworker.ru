package com.example.Coworker.ru.authenticationService.config.controller;

import com.example.Coworker.ru.authenticationService.config.entity.UserDTO;
import com.example.Coworker.ru.authenticationService.config.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class SecurityController {
    @Autowired
    UserService userService;

    @GetMapping("/home")
    public  String home(){
        return "This is Home";
    }

    @PostMapping("/register")
    public String create(@RequestParam("username") String username, @RequestParam("password") String password){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(username);
        userDTO.setPassword(password);
        return  userService.create(userDTO);
    }
}
