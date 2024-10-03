package com.example.household_book_server.repository;

import com.example.household_book_server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<Year> findByYearAndUser_Id(int year,Long userId);

    // userId에 해당하는 모든 Year 찾기
    List<Year> findByUserId(Long userId);
}
