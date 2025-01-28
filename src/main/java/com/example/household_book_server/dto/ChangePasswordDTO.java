package com.example.household_book_server.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String email;
    private String prevPassword;
    private String newPassword;
}
