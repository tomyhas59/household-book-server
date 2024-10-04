package com.example.household_book_server.model;

import com.example.household_book_server.dto.TransactionDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Month {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int month;
    private int year;
    private int budget;
    private String note;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "month", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;


}