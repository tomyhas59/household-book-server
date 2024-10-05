package com.example.household_book_server.controller;

import com.example.household_book_server.model.Transaction;
import com.example.household_book_server.service.TransactionService;
import org.hibernate.engine.spi.Resolution;
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


        Transaction added = transactionService.addTransaction(userId, monthId, transaction, year, month);

        if (added != null) {
            return ResponseEntity.ok(added);
        } else {
            return ResponseEntity.badRequest().build(); // monthId가 없을 경우 처리
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTransaction(
            @RequestParam Long userId,
            @RequestParam Long transactionId
    ) {
        try {
            transactionService.deleteTransaction(userId, transactionId);
            return ResponseEntity.ok("Transaction deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Transaction> updateTransaction(@RequestParam Long userId, @RequestParam Long transactionId, @RequestBody Transaction transaction) {
        Transaction updated = transactionService.updateTransaction(userId, transactionId, transaction);
        return ResponseEntity.ok(updated);
    }

}
