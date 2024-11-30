package com.example.Coworker.ru.mainService.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Coworkings",
    indexes = {
        @Index(name = "idx_name", columnList = "name")
    })
public class Coworking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "coworking_id", updatable = false, nullable = false)
    private UUID coworkingId;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false, name = "name")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "main_photo")
    private String mainPhoto;

    @Column(nullable = true)
    private Integer totalCapacity;

    @Column(columnDefinition = "VARCHAR(50)")
    private String address;

    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;
}
