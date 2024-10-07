package com.example.household_book_server.model;

import com.example.household_book_server.dto.TransactionDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference // 순환 참조 방지
    private User user;

    @OneToMany(mappedBy = "month", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // 직렬화 허용
    private List<Transaction> transactions;


}