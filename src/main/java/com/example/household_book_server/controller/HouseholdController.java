package com.example.household_book_server.controller;

import com.example.household_book_server.dto.MonthDTO;
import com.example.household_book_server.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;


    @GetMapping("/getYear")
    public ResponseEntity<List<MonthDTO>> getMonthsByUserIdAndYear(@RequestParam Long userId, @RequestParam Integer year) {
        List<MonthDTO> foundMonths = householdService.getMonthsByUserIdAndYear(userId, year);
        return ResponseEntity.ok(foundMonths);
    }

    @GetMapping("/getMonth")
    public ResponseEntity<MonthDTO> getMonthByUserIdAndYearAndMonth(
            @RequestParam Long userId,
            @RequestParam Integer year,
            @RequestParam Integer month) {

        MonthDTO foundMonth = householdService.getMonthByUserIdAndYearAndMonth(userId, year, month);

        return ResponseEntity.ok(foundMonth);

    }

}
