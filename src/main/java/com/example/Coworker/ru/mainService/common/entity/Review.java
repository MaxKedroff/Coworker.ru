package com.example.Coworker.ru.mainService.common.entity;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long review_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coworking_id")
    private Coworking coworking;

    private int mark;

    private String textField;
}
