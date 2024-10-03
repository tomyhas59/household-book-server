package com.example.household_book_server.controller;

import com.example.household_book_server.dto.MonthDTO;
import com.example.household_book_server.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    // userId, year, month로 Month 데이터 가져오기
    @GetMapping("/getMonth")
    public ResponseEntity<Optional<MonthDTO>> getMonthByUserIdAndYearAndMonth(
            @RequestParam Long userId,
            @RequestParam Integer year,
            @RequestParam Integer month) {

        Optional<MonthDTO> foundMonth = householdService.getMonthByUserIdAndYearAndMonth(userId, year, month);
        if (foundMonth.isPresent()) {
            return ResponseEntity.ok(foundMonth);
        }
        return ResponseEntity.notFound().build();
    }
}
