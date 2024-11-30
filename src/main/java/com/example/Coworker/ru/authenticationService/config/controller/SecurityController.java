package com.example.Coworker.ru.authenticationService.config.controller;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.entity.UserDTO;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtAuthentication;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtRequest;
import com.example.Coworker.ru.authenticationService.config.jwt.JwtResponse;
import com.example.Coworker.ru.authenticationService.config.jwt.RefreshJwtRequest;
import com.example.Coworker.ru.authenticationService.config.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Tag(name = "authentication", description = "полный цикл аутентификации,авторизации и регистрации пользователя")
public class SecurityController {
    @Autowired
    UserService userService;

    @Operation(
            summary = "регистрация пользователя",
            description = "регистрация пользователя c дальнейшей верификацией"
    )
    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody @Parameter(description = "необходимые данные для регистрации") UserDTO userDTO) throws MessagingException, AuthException {
        String res = userService.create(userDTO);
        if (res.equals("мы работает только с корпоративными почтами урфу, заканчивающимися на @urfu.me")){
            return ResponseEntity.status(400).body("мы работаем только с корпоративными почтами урфу, заканчивающимися на @urfu.me");
        }else if (res.equals("похоже, что аккаунт уже существует")){
            return ResponseEntity.status(409).body("похоже, что аккаунт уже существует");
        }
        return ResponseEntity.ok(res);
    }
    @Operation(
            summary = "аутентификация пользователя",
            description = "при верных данных выдается токен : во все запросы вставлять в bearer token "
    )
    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        try{
            final JwtResponse token = userService.login(authRequest);
            return ResponseEntity.ok(token);
        }catch (AuthException e){
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "получение нового токена(*на будущее) пока время не тратить"
    )
    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = userService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
    @GetMapping("/verify")
    public ResponseEntity<String> handleVerification(@RequestParam String code){
        if(userService.handleVerification(code)){
            return ResponseEntity.ok("успешная верификация");
        }
        return ResponseEntity.badRequest().body("something go wrong");
    }

    @Operation(
            summary = "обновление токена(*на будущее)"
    )
    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = userService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "checking authorization works with token")
    @PreAuthorize("hasAuthority('student')")
    @GetMapping("hello/student")
    public ResponseEntity<String> helloUser(@RequestHeader("Authorization") String header) {
//        final JwtAuthentication authInfo = userService.getAuthInfo();
//        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
        String token = header.substring(7);
        return ResponseEntity.ok(userService.getUserByToken(token));
    }


    @GetMapping("test")
    public ResponseEntity<String> checkCORS(){
        return ResponseEntity.ok("CORS настроен");
    }
}
