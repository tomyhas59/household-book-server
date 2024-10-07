package com.example.household_book_server.controller;

import com.example.household_book_server.model.Transaction;
import com.example.household_book_server.model.TransactionType;
import com.example.household_book_server.service.TransactionService;
import org.hibernate.engine.spi.Resolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        transactionService.deleteTransaction(userId, transactionId);
        return ResponseEntity.ok("Transaction deleted successfully");

    }

    @PutMapping("/update")
    public ResponseEntity<Transaction> updateTransaction(@RequestParam Long userId, @RequestParam Long transactionId, @RequestBody Transaction transaction) {
        Transaction updated = transactionService.updateTransaction(userId, transactionId, transaction);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllTransactions(@RequestParam Long userId, @RequestParam Long monthId, @RequestParam String type) {
        transactionService.deleteAllTransactions(userId, monthId, type);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam Long userId, @RequestParam Integer month, @RequestParam Integer year, @RequestParam String type) {
        List<Transaction> transactions = transactionService.getTransactions(userId, month, year, type);

        return ResponseEntity.ok(transactions);
    }
}
