package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.transaction.BizumDto;
import com.dicaro.dicarobank.dto.transaction.IssueTransactionDto;
import com.dicaro.dicarobank.dto.transaction.TransactionDto;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.Transaction;
import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import com.dicaro.dicarobank.services.transaction.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for transaction
 */
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionServiceImpl;
    private final AppUserServiceImpl appUserServiceImpl;

    /**
     * Obtains all outgoing transactions of an authenticated user by its dni
     * @param appUserDni the dni of the authenticated user
     * @return list of outgoing transactions
     */
    @GetMapping("/outgoing")
    public ResponseEntity<?> getOutgoingTransactionsAuthUser(@AuthenticationPrincipal String appUserDni) {
        try {
            List<TransactionDto> outgoingtransactionDtoList = transactionServiceImpl.getOutgoingTransactionsByAccountId(appUserDni);
            return ResponseEntity.ok(outgoingtransactionDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    /**
     * Obtains all incoming transactions of an authenticated user by its dni
     * @param appUserDni the dni of the authenticated user
     * @return list of incoming transactions
     */
    @GetMapping("/incoming")
    public ResponseEntity<?> getIncomingTransactionsAuthUser(@AuthenticationPrincipal String appUserDni) {
        try {
            List<TransactionDto> incomingTransactionDtoList = transactionServiceImpl.getIncomingTransactionsByAccountId(appUserDni);
            return ResponseEntity.ok(incomingTransactionDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    /**
     * Issue a transaction to an app user
     * @param appUserDni the dni of the authenticated user
     * @param issueTransactionDto the dto of the transaction to be issued
     * @return If the transaction was successful or not, with a message about it
     */
    @PostMapping("/issue/transaction")
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

    /**
     * Issue a bizum
     * @param appUserDni the dni of the authenticated user
     * @param bizumDto the dto of the transaction to be issued
     * @return If the transaction was successful or not, with a message about it
     */
    @PostMapping("/issue/bizum")
    public ResponseEntity<?> issueBizum(@AuthenticationPrincipal String appUserDni, @RequestBody BizumDto bizumDto) {

        Optional<AppUser> appUser = appUserServiceImpl.findAppUserByDni(appUserDni);

        if (appUser.isPresent()) {
            transactionServiceImpl.issueBizum(appUser.get(), bizumDto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Bizum realizado con éxito");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la operación");
    }
}
