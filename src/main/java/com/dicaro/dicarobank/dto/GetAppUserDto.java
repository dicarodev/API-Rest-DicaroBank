package com.dicaro.dicarobank.dto;

import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetAppUserDto {
    private String name;
    private Set<String> authorities;
}
