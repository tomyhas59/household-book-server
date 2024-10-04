package com.example.household_book_server.controller;

import com.example.household_book_server.model.Transaction;
import com.example.household_book_server.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // POST 요청으로 트랜잭션 추가
    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(
            @RequestParam(required = false) Long monthId,
            @RequestParam Long userId,
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestBody Transaction transaction) {


        Transaction savedTransaction = transactionService.addTransaction(userId, monthId, transaction, year, month);

        if (savedTransaction != null) {
            return ResponseEntity.ok(savedTransaction);
        } else {
            return ResponseEntity.badRequest().build(); // monthId가 없을 경우 처리
        }
    }
}
