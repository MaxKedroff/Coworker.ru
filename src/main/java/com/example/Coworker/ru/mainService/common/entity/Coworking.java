package com.example.Coworker.ru.mainService.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "coworkings")
public class Coworking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coworkingId;

    private String name;

    private String description;

    //фото тут

    private String address;

    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
