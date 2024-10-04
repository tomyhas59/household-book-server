package com.example.household_book_server.dto;

import com.example.household_book_server.model.TransactionType;
import lombok.Data;

@Data
public class TransactionDTO {
    private Long id;
    private int amount;
    private int date;
    private String description;
    private TransactionType type; // TransactionType
    private Long monthId; // 월 ID
}
