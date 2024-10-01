package com.example.household_book_server.service;

import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.UserRepository;
import com.example.household_book_server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private JwtUtil jwtUtil;


    public User registerUser(User user) {

        if (findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String authenticate(String email, String password) {
        User user = findByEmail(email);
        System.out.println("---------" + user);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // JWT 토큰 생성 및 반환
            return jwtUtil.generateToken(email);
        }
        throw new RuntimeException("로그인 실패: 잘못된 이메일 또는 비밀번호입니다.");
    }



    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
