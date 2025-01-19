package com.example.household_book_server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // getter, setter, toString, equals, hashCode 메서드를 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 생성
public class MonthDTO {
    private Long id;
    private Integer month;
    private Integer year;
    private Integer budget;
    private String note;
    private List<TransactionDTO> transactions;
}
