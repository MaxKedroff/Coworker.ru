package com.example.Coworker.ru.mainService.bookingService;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.repository.UserRepo;
import com.example.Coworker.ru.mainService.common.entity.Booking;
import com.example.Coworker.ru.mainService.common.entity.BookingRequest;
import com.example.Coworker.ru.mainService.common.entity.Coworking;
import com.example.Coworker.ru.mainService.common.repository.BookingRepo;
import com.example.Coworker.ru.mainService.common.repository.CoworkingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CoworkingRepo coworkingRepo;

    public void CreateNewBooking(BookingRequest request){
        if (request.getStartTime().isAfter(request.getEndTime())){
            throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");
        }

        User user = userRepo.findByUsername(request.getUsername());

        Coworking coworking = coworkingRepo.findById(UUID.fromString(request.getCoworkingId())).orElseThrow(() -> new IllegalArgumentException("Коворкинг с таким ID не найден"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCoworking(coworking);
        booking.setBookingDateStart(request.getStartTime());
        booking.setBookingDateEnd(request.getEndTime());

        bookingRepo.save(booking);
    }
}
