package com.example.household_book_server.service;

import com.example.household_book_server.model.Month;
import com.example.household_book_server.model.User;
import com.example.household_book_server.model.Year;
import com.example.household_book_server.repository.MonthRepository;
import com.example.household_book_server.repository.YearRepository;
import com.example.household_book_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthService {

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private UserRepository userRepository;

    public Month addOrUpdateBudgetAndNote(Long userId, int year, int month, Integer budget, String note) {
        // 사용자 조회
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 해당 연도 찾기
            Optional<Year> yearOptional = yearRepository.findByYearAndUser_Id(year, userId);
            Year foundYear;

            if (yearOptional.isPresent()) {
                // Year 데이터가 존재하는 경우
                foundYear = yearOptional.get();
            } else {
                // Year 데이터가 없으면 생성
                foundYear = new Year();
                foundYear.setUser(user);
                foundYear.setYear(year);
                foundYear = yearRepository.save(foundYear); // Year 저장
            }

            // 해당 월 찾기
            Optional<Month> monthOptional = monthRepository.findByMonthAndYearAndUserId(month, year, userId);
            if (monthOptional.isPresent()) {
                // 기존 월 데이터 업데이트
                Month existingMonth = monthOptional.get();
                // budget이 null인 경우 기존 값 유지
                if (budget != null) {
                    existingMonth.setBudget(budget);
                }
                // note가 null인 경우 기존 값 유지
                if (note != null) {
                    existingMonth.setNote(note);
                }
                return monthRepository.save(existingMonth);
            } else {
                // 새로운 월 데이터 생성
                Month newMonth = new Month();
                newMonth.setMonth(month);
                newMonth.setBudget(budget); // budget이 null이면 DB에 null로 저장
                newMonth.setNote(note); // note가 null이면 DB에 null로 저장
                newMonth.setYear(foundYear);
                newMonth.setUser(user);
                return monthRepository.save(newMonth);
            }
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

}
