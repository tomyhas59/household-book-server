package com.example.household_book_server.controller;

import com.example.household_book_server.dto.ChangePasswordDTO;
import com.example.household_book_server.model.User;
import com.example.household_book_server.service.UserService;
import com.example.household_book_server.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Object> login(@RequestBody User user, HttpServletResponse response) {
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

        // 🍪 쿠키 생성 및 설정
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true); // JS에서 접근 불가 (보안 핵심)
        cookie.setSecure(false); // HTTPS가 아닐 경우 false (배포 시 true 권장)
        cookie.setPath("/"); // 서비스 전역에서 사용
        cookie.setMaxAge(3600); // 1시간 유지

        response.addCookie(cookie); // 응답 헤더에 쿠키 추가

        // 사용자 정보를 담을 Map 객체 생성
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", existingUser.getEmail()); // 사용자 이메일
        userInfo.put("nickname", existingUser.getNickname()); // 사용자 닉네임
        userInfo.put("id", existingUser.getId()); // 사용자 ID

        return ResponseEntity.ok(userInfo); // 사용자 정보와 함께 응답 반환

    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO data) {

        String email = data.getEmail();
        String prevPassword = data.getPrevPassword();
        String newPassword = data.getNewPassword();
        userService.changePassword(email, prevPassword, newPassword);
        return ResponseEntity.ok("비밀번호 변경 성공");

    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 익명 사용자인 경우 처리
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String email = auth.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        // 프론트의 userState 구조에 맞게 데이터 구성
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", user.getEmail());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("id", user.getId());

        return ResponseEntity.ok(userInfo);
    }
}
