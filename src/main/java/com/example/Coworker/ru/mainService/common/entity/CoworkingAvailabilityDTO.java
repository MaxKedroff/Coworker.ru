package com.example.Coworker.ru.mainService.common.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoworkingAvailabilityDTO {
    private UUID coworkingId;
    private String name;
    private String location;
    private int totalCapacity;
    private int bookedCapacity;
    private int availableCapacity;
}
