package com.example.household_book_server.service;

import com.example.household_book_server.dto.MonthDTO;
import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.MonthRepository;
import com.example.household_book_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseholdService {

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private UserRepository userRepository;

    // userId, year, month로 Month 데이터 가져오기
    public Optional<MonthDTO> getMonthByUserIdAndYearAndMonth(Long userId, Integer year, Integer month) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Optional<Month> monthOptional = monthRepository.findByUserIdAndYearAndMonth(userId, year, month);
            if (monthOptional.isPresent()) {
                Month monthEntity = monthOptional.get();
               MonthDTO monthDTO=new MonthDTO();
                monthDTO.setId(monthEntity.getId());
                monthDTO.setMonth(monthEntity.getMonth());
                monthDTO.setYear(monthEntity.getYear());
                monthDTO.setBudget(monthEntity.getBudget());
                monthDTO.setNote(monthEntity.getNote());
                monthDTO.setTransactions(monthEntity.getTransactions());

                return Optional.of(monthDTO);
            }
        }
        return Optional.empty();
    }
}
