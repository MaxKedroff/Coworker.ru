package com.example.Coworker.ru.mainService.common.repository;

import com.example.Coworker.ru.mainService.common.entity.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
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

    @Query("SELECT b FROM Booking b WHERE b.coworking.coworkingId = :coworkingId")
    List<Booking> findAllByCoworkingId(@Param("coworkingId") UUID coworkingId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.coworking.coworkingId = :coworkingId")
    void deleteAllByCoworkingId(@Param("coworkingId") UUID coworkingId);
}