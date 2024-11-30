package com.example.Coworker.ru.mainService.common.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoworkingAvailabilityDTO {
    private Coworking coworking;
    private int availableCapacity;
}
