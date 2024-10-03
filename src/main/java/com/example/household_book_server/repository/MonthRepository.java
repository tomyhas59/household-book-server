package com.example.household_book_server.repository;

import com.example.household_book_server.model.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthRepository extends JpaRepository<Month, Long> {
    Optional<Month> findByMonthAndYearAndUserId(int month, int year, Long userId);
}
