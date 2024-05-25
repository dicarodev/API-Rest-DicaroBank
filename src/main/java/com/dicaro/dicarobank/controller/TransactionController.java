package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.IssueTransactionDto;
import com.dicaro.dicarobank.dto.TransactionConverter;
import com.dicaro.dicarobank.dto.TransactionDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionServiceImpl;
    private final AppUserServiceImpl appUserServiceImpl;
    private final AccountServiceImpl accountServiceImpl;
    private final TransactionConverter transactionConverter;

    @GetMapping("/outgoing")
    public ResponseEntity<?> getOutgoingTransactionsAuthUser(@AuthenticationPrincipal String appUserDni) {

        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        Optional<Account> account = accountServiceImpl.findAccountByAppUserId(appUser.isPresent() ? appUser.get().getId() : null);

        Optional<List<Transaction>> transactions = transactionServiceImpl.getOutgoingTransactionsByAccountId(account.isPresent() ? account.get().getId() : null);

        List<Transaction> outgoingTransactionList = transactions.orElse(null);
        List<TransactionDto> outgoingtransactionDtoList = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(outgoingTransactionList).size(); i++) {
            outgoingtransactionDtoList.add(transactionConverter.convertTransactionEntityToTransactionDto(outgoingTransactionList.get(i)));
        }


        return ResponseEntity.status(HttpStatus.OK).body(
                !outgoingtransactionDtoList.isEmpty() ? outgoingtransactionDtoList : ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/incoming")
    public ResponseEntity<?> getIncomingTransactionsAuthUser(@AuthenticationPrincipal String appUserDni) {

        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        Optional<Account> account = accountServiceImpl.findAccountByAppUserId(appUser.isPresent() ? appUser.get().getId() : null);

        Optional<List<Transaction>> transactions = transactionServiceImpl.getIncomingTransactionsByAccountId(account.isPresent() ? account.get().getId() : null);

        List<Transaction> incomingtransactionList = transactions.orElse(null);
        List<TransactionDto> incomingTransactionDtoList = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(incomingtransactionList).size(); i++) {
            incomingTransactionDtoList.add(transactionConverter.convertTransactionEntityToTransactionDto(incomingtransactionList.get(i)));
        }


        return ResponseEntity.status(HttpStatus.OK).body(
                !incomingTransactionDtoList.isEmpty() ? incomingTransactionDtoList : ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/issue")
    public ResponseEntity<?> issueTransaction(@AuthenticationPrincipal String appUserDni, @RequestBody IssueTransactionDto issueTransactionDto) {

        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        if (appUser.isPresent()) {
            Transaction transaction = transactionServiceImpl.issueTransaction(appUser.get(), issueTransactionDto);

            if (transaction != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Transferencia realizada con éxito");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la operación");
    }
}
