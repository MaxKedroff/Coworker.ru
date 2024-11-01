package com.example.Coworker.ru.mainService.common.entity;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reviews",
uniqueConstraints = @UniqueConstraint(columnNames = {"reviews_id"}),
indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_coworking_id", columnList = "coworking_id")
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reviews_id", updatable = false, nullable = false)
    public UUID reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_review_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coworking_id", nullable = false, foreignKey = @ForeignKey(name = "fk_review_coworking"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Coworking coworking;

    @Column(name = "rating", nullable = false, columnDefinition = "INT CHECK (rating BETWEEN 1 AND 5)")
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String reviewText;
}
