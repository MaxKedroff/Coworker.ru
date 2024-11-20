package com.example.Coworker.ru.mainService.common.entity;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "bookings",
uniqueConstraints = @UniqueConstraint(columnNames = {"booking_id"}),
indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_coworking_id", columnList = "coworking_id")
})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "booking_id", updatable = false, nullable = false)
    private UUID bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coworking_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Coworking coworking;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime bookingDateStart;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime bookingDateEnd;

    @Column(name = "is_expired", nullable = false)
    private Boolean isExpired = false;
}
