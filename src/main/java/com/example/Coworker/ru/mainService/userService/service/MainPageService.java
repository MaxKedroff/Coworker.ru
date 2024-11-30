package com.example.Coworker.ru.mainService.userService.service;

import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.common.entity.CoworkingAvailabilityDTO;
import com.example.Coworker.ru.mainService.common.repository.BookingRepo;
import com.example.Coworker.ru.mainService.common.repository.CoworkingRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MainPageService {

    @Autowired
    private CoworkingRepo coworkingRepo;

    @Autowired
    private BookingRepo bookingRepo;

    private static final int DEFAULT_CAPACITY = 15;

    public List<Coworking> getAllCoworkings(){
        return coworkingRepo.findAll();
    }

    public Coworking getCoworkingByName(String name){
        return coworkingRepo.findByName(name);
    }

    public Coworking getCoworkingById(UUID id) {
        return coworkingRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Коворкинг с id " + id + " не найден"));
    }

    public List<Coworking> findAvailableCoworkings(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, int capacity) {
        List<Coworking> allCoworkings = coworkingRepo.findAll();

        return allCoworkings.stream()
                .filter(coworking -> {
                    int coworkingCapacity = coworking.getTotalCapacity() != null
                            ? coworking.getTotalCapacity()
                            : DEFAULT_CAPACITY; // Используем вместимость по умолчанию

                    int bookedCapacity = bookingRepo.getTotalCapacityForCoworking(
                            coworking.getCoworkingId(), dateTimeStart, dateTimeEnd);

                    int availableCapacity = coworkingCapacity - bookedCapacity;
                    return availableCapacity >= capacity;
                })
                .collect(Collectors.toList());
    }

    public List<CoworkingAvailabilityDTO> findAvailableCoworkingsFix(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, int capacity) {
        List<Coworking> allCoworkings = coworkingRepo.findAll();
        return allCoworkings.stream()
                .map(coworking -> {
                    int coworkingCapacity = coworking.getTotalCapacity() != null
                            ? coworking.getTotalCapacity()
                            : DEFAULT_CAPACITY; // Используем вместимость по умолчанию

                    int bookedCapacity = bookingRepo.getTotalCapacityForCoworking(
                            coworking.getCoworkingId(), dateTimeStart, dateTimeEnd);

                    int availableCapacity = coworkingCapacity - bookedCapacity;
                    return new CoworkingAvailabilityDTO(
                      coworking.getCoworkingId(),
                      coworking.getName(),
                      coworking.getAddress(),
                      coworkingCapacity,
                      bookedCapacity,
                      availableCapacity
                    );
                })
                .filter(coworkingAvailabilityDTO -> coworkingAvailabilityDTO.getAvailableCapacity() >= capacity )
                .collect(Collectors.toList());
    }
}
