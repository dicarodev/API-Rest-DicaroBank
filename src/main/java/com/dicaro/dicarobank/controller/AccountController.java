package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.AcoountConverter;
import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.services.account.AccountServiceImpl;
import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AppUserServiceImpl appUserServiceImpl;
    private final AccountServiceImpl accountServiceImpl;
    private final AcoountConverter accountConverter;

    @GetMapping("/user")
    public ResponseEntity<?> getAuthUserAccount(@AuthenticationPrincipal String appUserDni) {

        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        if (appUser.isPresent()) {
            Optional<Account> account = accountServiceImpl.findAccountByAppUserId(appUser.get().getId());

            if (account.isPresent() && account.get().getAppUser().getId() == appUser.get().getId()) {
                return ResponseEntity.status(HttpStatus.OK).body(accountConverter.convertAccountEntityToAccountDto(account.get()));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
