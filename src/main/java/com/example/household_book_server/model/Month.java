package com.example.household_book_server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity  // 이 클래스는 JPA 엔티티이며, 데이터베이스의 테이블에 매핑됨
public class Month {
    @Id // 기본 키를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값은 자동으로 생성
    private Long id;

    private int month;
    private int year;
    private int budget;
    private String note;

    @ManyToOne
    @JsonBackReference // 순환 참조 방지 : 자식 객체에서 부모 객체를 직렬화하지 않도록 방지
    private User user;

    @OneToMany(mappedBy = "month",
            cascade = CascadeType.ALL, // 'Month' 엔티티에 대한 모든 변경 작업(삽입, 업데이트, 삭제 등)이 연관된 'Transaction' 엔티티에도 전파됨
            fetch = FetchType.LAZY)   //'Transaction' 엔티티는 'Month' 엔티티를 로드할 때 지연 로딩으로 처리되어 필요할 때만 데이터를 불러옴
    @JsonManagedReference  // 부모 객체에서 자식 객체를 직렬화할 수 있도록 허용
    private List<Transaction> transactions;


}