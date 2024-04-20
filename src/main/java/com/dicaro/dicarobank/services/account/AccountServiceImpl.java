package com.dicaro.dicarobank.services.account;

import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public void createNewAccount(AppUser appUser){
        Account account = Account.builder()
                .appUser(appUser)
                .build();

        try {
            accountRepository.save(account);
        }catch (DataIntegrityViolationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya tiene una cuenta");
        }
    }
    @Override
    public Optional<Account> findAccountByAppUserId(Long appUserId) {
        return accountRepository.findAccountByAppUser_Id(appUserId);
    }
}
