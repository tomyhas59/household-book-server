package com.example.household_book_server.service;

import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.MonthRepository;
import com.example.household_book_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MonthService {

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public Month createOrUpdateMonth(Long userId, Integer year, Integer month, Integer budget, String note) {
        // User를 찾기
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();


            // 해당 year와 month에 대한 Month 엔티티 찾기
            Optional<Month> existingMonth = monthRepository.findByUserIdAndYearAndMonth(userId, year, month);
            if (existingMonth.isPresent()) {
                // 기존 Month가 있다면 budget과 note를 수정
                Month monthEntity = existingMonth.get();
                if (budget != null && note == null) monthEntity.setBudget(budget);
                if (budget == null && note != null) monthEntity.setNote(note);
                if (budget != null && note != null) {
                    monthEntity.setBudget(budget);
                    monthEntity.setNote(note);
                }
                return monthRepository.save(monthEntity);
            } else {
                // 기존 Month가 없다면 새로운 Month 생성
                Month newMonth = new Month();
                newMonth.setYear(year);
                newMonth.setMonth(month);
                newMonth.setBudget(budget);
                newMonth.setNote(note);
                newMonth.setUser(user);
                return monthRepository.save(newMonth);
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Month createMonth(Long userId, Integer year, Integer month) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Month newMonth = new Month();
            newMonth.setYear(year);
            newMonth.setMonth(month);
            newMonth.setUser(user);
            return monthRepository.save(newMonth);
        }
        return null;
    }


}
