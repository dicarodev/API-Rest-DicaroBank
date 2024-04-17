package com.dicaro.dicarobank.dto;

import lombok.*;

import java.util.List;

/**
 * DTO for LogInResponse
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LogInResponseDto {
    private String dni;
    private List<String> authorities;
    private String token;
}
