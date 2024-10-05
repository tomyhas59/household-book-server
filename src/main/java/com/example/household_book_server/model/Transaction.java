package com.example.household_book_server.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    private int date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "monthId")
    private Month month;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}

