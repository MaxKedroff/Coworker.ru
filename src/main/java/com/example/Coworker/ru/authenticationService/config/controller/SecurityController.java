package com.example.Coworker.ru.authenticationService.config.controller;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.entity.UserDTO;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtRequest;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtResponse;
import com.example.Coworker.ru.authenticationService.config.jwt.RefreshJwtRequest;
import com.example.Coworker.ru.authenticationService.config.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public String create(@RequestBody UserDTO userDTO){
        return userService.create(userDTO);
    }
    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = userService.login(authRequest);
        return ResponseEntity.ok(token);
    }
    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = userService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = userService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
