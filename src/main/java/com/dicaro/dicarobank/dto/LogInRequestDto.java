package com.dicaro.dicarobank.dto;

import lombok.*;

/**
 * DTO for LogInRequest
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
public class LogInRequestDto {
    private String dni;
    private String password;
}
