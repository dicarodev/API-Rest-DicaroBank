package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.AccountDto;
import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.services.Account.AccountServiceImpl;
import com.dicaro.dicarobank.services.AppUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;
    private final AppUserServiceImpl appUserService;

    @GetMapping("/user")
    public ResponseEntity<?> getAppUserAccount(@AuthenticationPrincipal String appUserDni) {

        Optional<AppUser> appUser = appUserService.findAppUserByDni(appUserDni);

        if (appUser.isPresent()) {
            Optional<Account> account = accountService.findAccountById(appUser.get().getId());

            if (account.isPresent() && account.get().getAppUser().getId() == appUser.get().getId()) {
                return ResponseEntity.ok().body(
                        AccountDto.builder()
                                .id(account.get().getId())
                                .accountNumber(account.get().getAccountNumber())
                                .balance(account.get().getBalance())
                                .build());
            }
        }

        return ResponseEntity.notFound().build();
    }
}
