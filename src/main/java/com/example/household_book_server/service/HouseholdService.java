package com.example.household_book_server.service;

import com.example.household_book_server.dto.MonthDTO;
import com.example.household_book_server.dto.TransactionDTO;
import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.MonthRepository;
import com.example.household_book_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Optional<Month> monthOptional = monthRepository.findByUserIdAndYearAndMonth(userId, year, month);
            if (monthOptional.isPresent()) {
                Month monthEntity = monthOptional.get();

                MonthDTO monthDTO = new MonthDTO();

                monthDTO.setId(monthEntity.getId());
                monthDTO.setMonth(monthEntity.getMonth());
                monthDTO.setYear(monthEntity.getYear());
                monthDTO.setBudget(monthEntity.getBudget());
                monthDTO.setNote(monthEntity.getNote());

                // TransactionDTO 변환
                List<TransactionDTO> transactionDTOs = monthEntity.getTransactions().stream()
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
        }
        return new MonthDTO(); //없을 시 빈 데이터 반환

    }
}
