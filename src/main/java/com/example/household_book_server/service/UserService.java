package com.example.household_book_server.service;

import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이메일이 이미 존재합니다.");
        }


        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            throw new IllegalArgumentException("닉네임이 이미 존재합니다.");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void changePassword(String email, String prevPassword, String newPassword) {
        Optional<User> currentUserOpt = userRepository.findByEmail(email);
        // 사용자 없으면 예외 처리
        if (currentUserOpt.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        User currentUser = currentUserOpt.get();


        if (!passwordEncoder.matches(prevPassword, currentUser.getPassword())) {
            throw new RuntimeException("기존 비밀번호가 다릅니다");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);

        currentUser.setPassword(encodedNewPassword);
        userRepository.save(currentUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
