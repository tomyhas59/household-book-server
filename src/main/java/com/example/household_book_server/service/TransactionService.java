package com.example.household_book_server.service;

import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.Transaction;
import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.MonthRepository;
import com.example.household_book_server.repository.TransactionRepository;
import com.example.household_book_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction addTransaction(Long userId, Long monthId, Transaction transaction, Integer year, Integer month) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 해당 year와 month에 대한 Month 엔티티 찾기
            Month monthEntity;

            if (monthId != null) {
                // monthId가 있는 경우 기존 Month를 찾음
                Optional<Month> existingMonthOptional = monthRepository.findById(monthId);
                if (existingMonthOptional.isPresent()) {
                    monthEntity = existingMonthOptional.get();
                } else {
                    throw new RuntimeException("Month not found"); // Month가 존재하지 않을 경우 예외 처리
                }
            } else {
                // 기존 Month가 없다면 새로운 Month 생성
                monthEntity = new Month();
                monthEntity.setYear(year);
                monthEntity.setMonth(month);
                monthEntity.setUser(user);
                monthEntity = monthRepository.save(monthEntity);
            }
            transaction.setMonth(monthEntity);
            return transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Transactional
    public void deleteTransaction(Long userId, Long transactionId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();

            if (!transaction.getMonth().getUser().getId().equals(userId)) {
                throw new RuntimeException("transaction does not belong to the user");
            }
            transactionRepository.delete(transaction);
        }
    }

    @Transactional
    public Transaction updateTransaction(Long userId, Long transactionId, Transaction transaction) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);

        if (transactionOptional.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }

        Transaction existingTransaction = transactionOptional.get();

        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setDate(transaction.getDate());

        return transactionRepository.save(existingTransaction);
    }

}

