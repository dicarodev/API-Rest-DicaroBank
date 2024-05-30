package com.dicaro.dicarobank.dto.transaction;

import com.dicaro.dicarobank.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    public TransactionDto convertTransactionEntityToTransactionDto(Transaction transaction){
        return TransactionDto.builder()
                .transactionDate(transaction.getTransactionDate())
                .amount(transaction.getAmount())
                .detail(transaction.getDetail())
                .originAccountNumber(transaction.getOriginAccount().getAccountNumber())
                .destinyAccountNumber(
                        transaction.getDestinyAccount() == null ? "Envio Bizum" :
                        transaction.getDestinyAccount().getAccountNumber())
                .build();
    }
}
