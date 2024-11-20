package com.example.Coworker.ru.mainService.doorService;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.repository.UserRepo;
import com.example.Coworker.ru.mainService.common.entity.Booking;
import com.example.Coworker.ru.mainService.common.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class DoorService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;


    public boolean shouldActivateScanner(User user){
        Booking closestBooking = getClosestBooking(user);
        System.out.println(closestBooking.getBookingDateStart());
        LocalDateTime bookingStartTime = closestBooking.getBookingDateStart();
        return isFiveMinutesBeforeBooking(bookingStartTime);
    }

    public boolean isValidQRCode(String qrCode) {
        // Логика для проверки QR-кода.
        return true;
    }

    public Booking getClosestBooking(User user) {
        LocalDateTime now = LocalDateTime.now();
        return user.getBookings().stream()
                .filter(booking -> Boolean.FALSE.equals(booking.getIsExpired())) // Исключить истёкшие брони
                .filter(booking -> booking.getBookingDateStart().isAfter(now)) // Исключить брони с прошедшим временем
                .min(Comparator.comparing(Booking::getBookingDateStart)) // Найти минимальную разницу по времени начала
                .orElseThrow(() -> new IllegalStateException("Нет доступных бронирований"));
    }



    private boolean isFiveMinutesBeforeBooking(LocalDateTime bookingStart) {
        LocalDateTime now = LocalDateTime.now();
        long minutesUntilBooking = java.time.Duration.between(now, bookingStart).toMinutes();
        return minutesUntilBooking <= 5 && minutesUntilBooking > 0;
    }
}
