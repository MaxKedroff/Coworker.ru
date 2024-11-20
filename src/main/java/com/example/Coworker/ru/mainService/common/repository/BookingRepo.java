package com.example.Coworker.ru.mainService.common.repository;

import com.example.Coworker.ru.mainService.common.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepo extends JpaRepository<Booking, UUID> {
}
