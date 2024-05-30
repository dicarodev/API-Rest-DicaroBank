package com.dicaro.dicarobank.dto.appUser;

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
