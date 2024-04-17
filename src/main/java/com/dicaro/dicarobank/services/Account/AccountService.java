package com.dicaro.dicarobank.services.Account;

import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;

import java.util.Optional;

public interface AccountService {
    void createNewAccount(AppUser appUser);
    Optional<Account> findAccountById(Long id);
}
