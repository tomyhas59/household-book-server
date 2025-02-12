package com.example.household_book_server.controller;

import com.example.household_book_server.dto.ChangePasswordDTO;
import com.example.household_book_server.model.User;
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

@RequestMapping("/api") // 이 컨트롤러 내의 모든 메서드는 "/api" 경로로 시작
public class AuthController {

    @Autowired // Spring이 해당 필드에 필요한 의존성을 자동으로 주입함
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
        // 사용자 이메일로 기존 사용자 검색
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 이메일입니다.");
        }
        // 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 다릅니다.");
        }


        // 패스워드가 일치하면 JWT 토큰 생성
        String token = jwtUtil.generateToken(existingUser.getEmail());

        // 사용자 정보를 담을 Map 객체 생성
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token); // JWT 토큰
        userInfo.put("email", existingUser.getEmail()); // 사용자 이메일
        userInfo.put("nickname", existingUser.getNickname()); // 사용자 닉네임
        userInfo.put("id", existingUser.getId()); // 사용자 ID

        return ResponseEntity.ok(userInfo); // 사용자 정보와 함께 응답 반환


    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO data) {
        try {
            String email = data.getEmail();
            String prevPassword = data.getPrevPassword();
            String newPassword = data.getNewPassword();
            userService.changePassword(email, prevPassword, newPassword);
            return ResponseEntity.ok("비밀번호 변경 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}
