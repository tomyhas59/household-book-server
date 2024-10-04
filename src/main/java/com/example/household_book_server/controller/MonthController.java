package com.example.household_book_server.controller;

import com.example.household_book_server.model.Month;
import com.example.household_book_server.service.MonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MonthController {

    @Autowired
    private MonthService monthService;

    // 월 정보 저장 또는 업데이트 API
    @PostMapping("/saveNoteOrBudget")
    public ResponseEntity<Month> saveOrUpdateMonth(
            @RequestParam Long userId,
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam(required = false) Integer budget,
            @RequestParam(required = false) String note) {

        Month updatedMonth = monthService.createOrUpdateMonth(userId, year, month, budget, note);

        return ResponseEntity.ok(updatedMonth);
    }

    @PostMapping("/createMonth")
    public ResponseEntity<Month> createMonth(
            @RequestParam Long userId,
            @RequestParam Integer year,
            @RequestParam Integer month) {

        Month updatedMonth = monthService.createMonth(userId, year, month);

        return ResponseEntity.ok(updatedMonth);
    }

}