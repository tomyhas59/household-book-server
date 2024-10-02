package com.example.household_book_server.controller;

import com.example.household_book_server.model.User;
import com.example.household_book_server.repository.UserRepository;
import com.example.household_book_server.service.UserService;
import com.example.household_book_server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
       User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser !=null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            String token = jwtUtil.generateToken(existingUser.getEmail());

            // 사용자 정보를 담을 Map 객체 생성
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("token", token);
            userInfo.put("email", existingUser.getEmail());
            userInfo.put("nickname", existingUser.getNickname()); // 사용자 닉네임 추가

            return ResponseEntity.ok(userInfo); // 사용자 정보 반환
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }


}
