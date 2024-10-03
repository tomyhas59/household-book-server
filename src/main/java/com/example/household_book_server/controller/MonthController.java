package com.example.household_book_server.controller;

import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.User;
import com.example.household_book_server.model.Year;
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
            @RequestParam int yearValue,
            @RequestParam int monthValue,
            @RequestParam(required = false) Integer budget,
            @RequestParam(required = false) String note) {

        Month updatedMonth = monthService.addOrUpdateBudgetAndNote(userId, yearValue, monthValue, budget, note);

        return ResponseEntity.ok(updatedMonth);
    }
}
