package com.example.Coworker.ru.mainService.bookingService;

import com.example.Coworker.ru.authenticationService.config.service.UserService;
import com.example.Coworker.ru.mainService.common.entity.Booking;
import com.example.Coworker.ru.mainService.common.entity.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main/bookings")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('student')")
    @PostMapping("/create-booking")
    public ResponseEntity<String> createNewBooking(@RequestBody BookingRequest request, @RequestHeader("Authorization") String header){
        String token = header.substring(7);
        String username = userService.getUserByToken(token);
        request.setUsername(username);
        try {
            bookingService.CreateNewBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Бронь успешно создана");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }
}
