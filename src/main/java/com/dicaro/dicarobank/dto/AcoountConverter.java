package com.dicaro.dicarobank.dto;

import com.dicaro.dicarobank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AcoountConverter {

    public AccountDto convertAccountEntityToAccountDto(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build();
    }
}
