package com.example.household_book_server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    FOOD("식비"),
    NECESSITIES("생필품"),
    CULTURE("문화생활"),
    TRANSPORTATION("교통비"),
    MEDICAL_ETC("의료 및 기타"),
    SAVINGS("저축"),
    FIXED_EXPENSE("고정 지출"),
    INCOME("수입");

    private final String koreanLabel;

    TransactionType(String koreanLabel) {
        this.koreanLabel = koreanLabel;
    }

    @JsonValue
    public String getKoreanLabel() {
        return koreanLabel;
    }

    @JsonCreator
    public static TransactionType fromString(String label) {
        for (TransactionType type : TransactionType.values()) {
            if (type.koreanLabel.equals(label) || type.name().equals(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + label);
    }
}
