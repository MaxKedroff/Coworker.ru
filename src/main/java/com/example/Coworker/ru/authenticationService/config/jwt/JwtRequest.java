package com.example.Coworker.ru.authenticationService.config.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {

    private String email;
    private String password;
}
