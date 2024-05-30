package com.dicaro.dicarobank.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime transactionDate;
    private double amount;
    private String detail;
    private String originAccountNumber;
    private String destinyAccountNumber;
}
