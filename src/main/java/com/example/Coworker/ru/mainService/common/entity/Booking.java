package com.example.Coworker.ru.mainService.common.entity;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coworking_id")
    private Coworking coworking;

    private LocalDate bookingDateStart;

    private LocalDate bookingDateEnd;
}
