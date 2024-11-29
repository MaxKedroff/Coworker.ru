package com.example.Coworker.ru.mainService.common.entity;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AvailabilityRequest {

    private LocalDateTime dateTimeStart;

    private LocalDateTime dateTimeEnd;

    private int capacity;
}