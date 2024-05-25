package com.dicaro.dicarobank.services.account;

import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;

import java.util.Optional;

public interface AccountService {
    void createNewAccount(AppUser appUser);
    Optional<Account> findAccountByAppUserId(Long id);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
