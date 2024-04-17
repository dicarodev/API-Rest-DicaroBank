package com.dicaro.dicarobank.dto;

import com.dicaro.dicarobank.model.Transaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long id;
    private int accountNumber;
    private double balance;
}
