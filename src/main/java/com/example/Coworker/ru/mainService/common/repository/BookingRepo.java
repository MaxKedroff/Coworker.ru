package com.example.Coworker.ru.mainService.common.repository;

import com.example.Coworker.ru.mainService.common.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BookingRepo extends JpaRepository<Booking, UUID> {

    @Query("SELECT COALESCE(SUM(b.capacity), 0) FROM Booking b " +
            "WHERE b.coworking.coworkingId = :coworkingId " +
            "AND b.bookingDateStart < :dateTimeEnd " +
            "AND b.bookingDateEnd > :dateTimeStart")
    int getTotalCapacityForCoworking(
            @Param("coworkingId") UUID coworkingId,
            @Param("dateTimeStart") LocalDateTime dateTimeStart,
            @Param("dateTimeEnd") LocalDateTime dateTimeEnd);
}