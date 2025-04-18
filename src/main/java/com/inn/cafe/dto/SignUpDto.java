package com.inn.cafe.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String contactNumber;
    private String email;
    private String password;
    private String status;
}
