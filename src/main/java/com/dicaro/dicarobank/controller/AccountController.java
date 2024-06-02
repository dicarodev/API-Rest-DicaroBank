package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.account.AccountDto;
import com.dicaro.dicarobank.services.account.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for account
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountServiceImpl;

    /**
     * Obtains the account of the authenticated user by its dni
     * @param appUserDni the dni of the authenticated user
     * @return accountDto the account of the authenticated user
     */
    @GetMapping("/user")
    public ResponseEntity<?> getAuthUserAccount(@AuthenticationPrincipal String appUserDni) {
        try {
            AccountDto accountDto = accountServiceImpl.getAuthUserAccount(appUserDni);
            return ResponseEntity.status(HttpStatus.OK).body(accountDto);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }
}
