package com.dicaro.dicarobank.dto.transaction;

import com.dicaro.dicarobank.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    /**
     * Convert Transaction Entity to Transaction DTO
     * @param transaction the transaction entity to convert
     * @return the transaction dto
     */
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
