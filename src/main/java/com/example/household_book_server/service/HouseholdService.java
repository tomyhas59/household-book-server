package com.example.household_book_server.service;

import com.example.household_book_server.dto.MonthDTO;
import com.example.household_book_server.dto.TransactionDTO;
import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.MonthRepository;
import com.example.household_book_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseholdService {

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private UserRepository userRepository;

    public List<MonthDTO> getMonthsByUserIdAndYear(Long userId, Integer year) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Month> months = monthRepository.findByUserIdAndYear(userId, year);
            return months.stream().map(month -> {
                MonthDTO monthDTO = new MonthDTO();
                monthDTO.setId(month.getId());
                monthDTO.setMonth(month.getMonth());
                monthDTO.setYear(month.getYear());
                monthDTO.setBudget(month.getBudget());
                monthDTO.setNote(month.getNote());

                // TransactionDTO 변환
                List<TransactionDTO> transactionDTOs = month.getTransactions().stream()
                        .map(transaction -> {
                            TransactionDTO transactionDTO = new TransactionDTO();
                            transactionDTO.setId(transaction.getId());
                            transactionDTO.setAmount(transaction.getAmount());
                            transactionDTO.setDate(transaction.getDate());
                            transactionDTO.setDescription(transaction.getDescription());
                            transactionDTO.setType(transaction.getType());
                            transactionDTO.setMonthId(transaction.getMonth().getId());
                            return transactionDTO;
                        }).collect(Collectors.toList());

                monthDTO.setTransactions(transactionDTOs);
                return monthDTO;
            }).collect(Collectors.toList());
        }
        return List.of(); //없을 시 빈 리스트 반환
    }

    public MonthDTO getMonthByUserIdAndYearAndMonth(Long userId, Integer year, Integer month) {
        // 해당 userId로 유저가 존재하는지 확인
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // userId, year, month로 Month 엔티티 조회
            Optional<Month> monthOptional = monthRepository.findByUserIdAndYearAndMonth(userId, year, month);

            Month monthEntity;

            if (monthOptional.isPresent()) {
                // 존재할 경우, 해당 Month 사용
                monthEntity = monthOptional.get();
            } else {
                // 존재하지 않을 경우, 새로운 Month 생성
                monthEntity = new Month();
                monthEntity.setUser(user);
                monthEntity.setYear(year);
                monthEntity.setMonth(month);

                // 새로운 Month를 DB에 저장
                monthRepository.save(monthEntity);
            }

            // MonthDTO 변환
            MonthDTO monthDTO = new MonthDTO();
            monthDTO.setId(monthEntity.getId());
            monthDTO.setMonth(monthEntity.getMonth());
            monthDTO.setYear(monthEntity.getYear());
            monthDTO.setBudget(monthEntity.getBudget());
            monthDTO.setNote(monthEntity.getNote());

            // TransactionDTO 변환 (NullPointerException 방지)
            List<TransactionDTO> transactionDTOs = Optional.ofNullable(monthEntity.getTransactions())
                    .orElse(Collections.emptyList()) // transactions가 null일 경우 빈 리스트로 대체
                    .stream()
                    .map(transaction -> {
                        TransactionDTO transactionDTO = new TransactionDTO();
                        transactionDTO.setId(transaction.getId());
                        transactionDTO.setAmount(transaction.getAmount());
                        transactionDTO.setDate(transaction.getDate());
                        transactionDTO.setDescription(transaction.getDescription());
                        transactionDTO.setType(transaction.getType());
                        transactionDTO.setMonthId(transaction.getMonth().getId()); // monthId 설정
                        return transactionDTO;
                    }).collect(Collectors.toList());

            monthDTO.setTransactions(transactionDTOs);
            return monthDTO;
        }
        return new MonthDTO(); // 유저가 없으면 빈 MonthDTO 반환
    }

}
