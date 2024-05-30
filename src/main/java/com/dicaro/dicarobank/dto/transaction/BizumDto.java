package com.dicaro.dicarobank.dto.transaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BizumDto {
    private double amount;
    private String detail;
}
