package com.dicaro.dicarobank.dto.transaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueTransactionDto {
        private double amount;
        private String detail;
        private String destinyAccountNumber;
}
