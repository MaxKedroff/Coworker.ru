package com.example.Coworker.ru;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Coworker api service",
                description = "документация rest-full сервиса CoworkerApi",
                version = "1.0.0",
                contact = @Contact(
                        name = "Kedrov Maksim",
                        email = "kedrov.maksim2005@gmail.com",
                        url = "https://t.me/maxKedroff"
                )
        )
)
public class OpenApiConfig {
}
