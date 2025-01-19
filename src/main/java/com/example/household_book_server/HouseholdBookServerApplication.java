// 이 클래스가 속한 패키지 경로 선언
package com.example.household_book_server;

// Spring Boot 애플리케이션 실행에 필요한 클래스 import

import org.springframework.boot.SpringApplication; // 애플리케이션 실행
import org.springframework.boot.autoconfigure.SpringBootApplication; // 자동 설정 활성화

// Spring Boot 애플리케이션을 시작하는 클래스
@SpringBootApplication // 애플리케이션의 자동 설정 및 컴포넌트 스캔 활성화
public class HouseholdBookServerApplication {

    // 애플리케이션의 진입점인 main 메서드
    public static void main(String[] args) {
        // Spring Boot 애플리케이션 실행
        SpringApplication.run(HouseholdBookServerApplication.class, args);
    }
}
