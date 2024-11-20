package com.example.Coworker.ru.mainService.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private String username;        // UUID пользователя
    private String coworkingId;   // UUID коворкинга
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}