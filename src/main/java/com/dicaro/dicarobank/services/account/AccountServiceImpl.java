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

/**
 * Implementation of {@link AccountService} interface
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final AppUserServiceImpl appUserServiceImpl;

    /**
     * Creates a new account when registering a new user in the app and assigns it to the user in the database
     * @param appUser the user that will be assigned to the account
     * @throws ResponseStatusException if the user already has an account
     */
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

    /**
     * Finds an account by the user id in the database
     * @param appUserId the id of the user that owns the account
     * @return the account of the user
     */
    @Override
    public Optional<Account> findAccountByAppUserId(Long appUserId) {
        return accountRepository.findAccountByAppUser_Id(appUserId);
    }

    /**
     * Finds the account of the authenticated user in the database
     * @param appUserDni the dni of the authenticated user
     * @return the account of the authenticated user
     * @throws ResponseStatusException if the user does not exist or if the user does not have an account
     */
    @Override
    public AccountDto getAuthUserAccount(String appUserDni) {
        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        if (appUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        Optional<Account> account = findAccountByAppUserId(appUser.get().getId());

        if (account.isEmpty() || !(account.get().getAppUser().getId() == appUser.get().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }

        return accountConverter.convertAccountEntityToAccountDto(account.get());
    }

    /**
     * Finds an account by the account number in the database
     * @param accountNumber the number of the account
     * @return the account of the account number
     * @throws ResponseStatusException if the account does not exist
     */
    @Override
    public Optional<Account> findAccountByAccountNumber(String accountNumber) {
        try {
            return accountRepository.findAccountByAccountNumber(accountNumber);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta no existe");
        }
    }
}
