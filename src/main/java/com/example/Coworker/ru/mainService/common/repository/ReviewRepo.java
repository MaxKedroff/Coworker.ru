package com.example.Coworker.ru.mainService.common.repository;

import com.example.Coworker.ru.mainService.common.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
