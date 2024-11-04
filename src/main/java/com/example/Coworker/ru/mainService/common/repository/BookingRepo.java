package com.example.Coworker.ru.mainService.common.repository;

import com.example.Coworker.ru.mainService.common.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
