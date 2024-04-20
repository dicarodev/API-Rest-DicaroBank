package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.Transaction;
import com.dicaro.dicarobank.services.account.AccountServiceImpl;
import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import com.dicaro.dicarobank.services.transaction.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionServiceImpl;
    private final AppUserServiceImpl appUserServiceImpl;
    private final AccountServiceImpl accountServiceImpl;

    @GetMapping("/user/account")
    public ResponseEntity<?> getAllOriginTransactionsAuthUser(@AuthenticationPrincipal String appUserDni) {

        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        Optional<Account> account = accountServiceImpl.findAccountByAppUserId(
                appUser.isPresent() ? appUser.get().getId() : null);

        Optional<List<Transaction>> originTransactions = transactionServiceImpl.getOriginTransactionsByAccountId(
                account.isPresent() ? account.get().getId() : null);

        return ResponseEntity.status(HttpStatus.OK).body(
                originTransactions.isPresent() ? originTransactions.get() : ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
