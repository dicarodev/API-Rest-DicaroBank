package com.dicaro.dicarobank.services.account;

import com.dicaro.dicarobank.dto.account.AccountDto;
import com.dicaro.dicarobank.dto.account.AccountConverter;
import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.repository.AccountRepository;
import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final AppUserServiceImpl appUserServiceImpl;

    @Override
    public void createNewAccount(AppUser appUser) {
        try {
            Account account = Account.builder()
                    .appUser(appUser)
                    .build();

            accountRepository.save(account);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya tiene una cuenta");
        }
    }

    @Override
    public Optional<Account> findAccountByAppUserId(Long appUserId) {
        return accountRepository.findAccountByAppUser_Id(appUserId);
    }

    @Override
    public AccountDto getAuthUserAccount(String appUserDni) {
        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        if (appUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        Optional<Account> account = accountRepository.findAccountByAppUser_Id(appUser.get().getId());

        if (account.isEmpty() || !(account.get().getAppUser().getId() == appUser.get().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }

        return accountConverter.convertAccountEntityToAccountDto(account.get());
    }
    @Override
    public Optional<Account> findAccountByAccountNumber(String accountNumber) {
        try {
            return accountRepository.findAccountByAccountNumber(accountNumber);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe");
        }
    }
}
