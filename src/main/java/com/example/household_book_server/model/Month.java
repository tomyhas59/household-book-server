package com.example.household_book_server.model;

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
    private int budget;
    private String note;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "month", cascade = CascadeType.ALL)
    private List<Transaction> transactions;


}
