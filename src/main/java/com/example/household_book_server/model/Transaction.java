package com.example.household_book_server.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference // 순환 참조 방지
    private Month month;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}

