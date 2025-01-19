package com.example.household_book_server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    FOOD("식비"),            // 거래 유형: 식비
    NECESSITIES("생필품"),   // 거래 유형: 생필품
    CULTURE("문화생활"),     // 거래 유형: 문화생활
    TRANSPORTATION("교통비"),// 거래 유형: 교통비
    MEDICAL_ETC("의료 및 기타"), // 거래 유형: 의료 및 기타
    SAVINGS("저축"),        // 거래 유형: 저축
    FIXED_EXPENSE("고정 지출"), // 거래 유형: 고정 지출
    INCOME("수입");         // 거래 유형: 수입

    private final String koreanLabel; // 각 거래 유형의 한국어 라벨

    // 생성자: 각 거래 유형에 한국어 라벨을 할당
    TransactionType(String koreanLabel) {
        this.koreanLabel = koreanLabel;
    }

    // 직렬화 시 사용할 메서드: 거래 유형을 한국어 라벨로 변환
    @JsonValue
    public String getKoreanLabel() {
        return koreanLabel;
    }

    // 역직렬화 시 사용할 메서드: 문자열을 거래 유형(enum)으로 변환
    @JsonCreator
    public static TransactionType fromString(String label) {
        // 주어진 문자열(label)을 기준으로 해당하는 거래 유형을 반환
        for (TransactionType type : TransactionType.values()) {
            if (type.koreanLabel.equals(label) || type.name().equals(label)) {
                return type;
            }
        }
        // 매칭되는 거래 유형이 없으면 예외를 던짐
        throw new IllegalArgumentException("Unknown enum type " + label);
    }
}
