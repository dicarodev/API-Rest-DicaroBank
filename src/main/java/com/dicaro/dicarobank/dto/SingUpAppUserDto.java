package com.dicaro.dicarobank.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SingUpAppUserDto {
    private String dni;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
}
