package ru.itis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDto {
    private String name;
    private String email;
    private String password;
}
