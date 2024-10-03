package com.example.household_book_server.dto;

import com.example.household_book_server.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthDTO {
    private Long id;
    private Integer month;
    private Integer year;
    private Integer budget;
    private String note;
    private List<Transaction> transactions;
}
