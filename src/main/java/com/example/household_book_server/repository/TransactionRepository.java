package com.example.household_book_server.repository;

import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
