package com.example.household_book_server.repository;

import com.example.household_book_server.model.Month;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonthRepository extends JpaRepository<Month, Long> {
    Optional<Month> findByUserIdAndYearAndMonth(Long userId, Integer year, Integer month);

    List<Month> findByUserIdAndYear(Long userId, Integer year);
}
