package com.dicaro.dicarobank.dto.account;

import com.dicaro.dicarobank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    /**
     * Convert account entity to account dto
     * @param account the account entity to convert
     * @return the account dto converted
     */
    public AccountDto convertAccountEntityToAccountDto(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build();
    }
}
