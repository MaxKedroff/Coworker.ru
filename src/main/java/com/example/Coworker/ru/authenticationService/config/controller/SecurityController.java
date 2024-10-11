package com.example.Coworker.ru.authenticationService.config.controller;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.entity.UserDTO;
import com.example.Coworker.ru.authenticationService.config.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "authentication", description = "Operations related to authentication and authorization")
public class SecurityController {
    @Autowired
    UserService userService;

    @Operation(summary = "checking auth controller works", responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))))
    @GetMapping("/home")
    public  String home(){
        return "This is Home";
    }

    @Operation(summary = "register new user", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    @PostMapping("/register")
    public String create(@RequestParam("username") String username, @RequestParam("password") String password){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(username);
        userDTO.setPassword(password);
        return  userService.create(userDTO);
    }
}
