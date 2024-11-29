package com.example.Coworker.ru.mainService.common.entity;

import lombok.Data;

@Data
public class CoworkingRequest {
    private String name;

    private String description;

    private String address;

    private int totalCapacity;
}
