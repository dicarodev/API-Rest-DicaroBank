package com.dicaro.dicarobank.services.transaction;

import com.dicaro.dicarobank.dto.transaction.BizumDto;
import com.dicaro.dicarobank.dto.transaction.IssueTransactionDto;
import com.dicaro.dicarobank.dto.transaction.TransactionConverter;
import com.dicaro.dicarobank.dto.transaction.TransactionDto;
import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.Transaction;
import com.dicaro.dicarobank.repository.TransactionRepository;
import com.dicaro.dicarobank.services.account.AccountServiceImpl;
import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of TransactionService interface
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AppUserServiceImpl appUserServiceImpl;
    private final AccountServiceImpl accountServiceImpl;
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;

    /**
     * Obtains the list of outgoing transactions of an account based on the appUserDni parameter and converts it to a list of TransactionDto objects using the transactionConverter.
     * @param appUserDni the dni of the app user whose outgoing transactions are to be retrieved.
     * @return a list of TransactionDto objects representing the outgoing transactions of the account.
     * @throws ResponseStatusException if the app user is not found or if the account is not found for the given app user.
     */
    @Override
    public List<TransactionDto> getOutgoingTransactionsByAccountId(String appUserDni) {
        AppUser appUser = appUserServiceImpl.findAppUserByDni(appUserDni)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el usuario"));

        Account account = accountServiceImpl.findAccountByAppUserId(appUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la cuenta"));

        // Retrieve all transactions for the account or an empty list if not found
        List<Transaction> transactions = transactionRepository.findTransactionsByOriginAccountIdOrderByTransactionDate(account.getId()).orElse(Collections.emptyList());

        // Convert transaction to DTO
        return transactions.stream()
                .map(transactionConverter::convertTransactionEntityToTransactionDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtains the list of incoming transactions of an account based on the appUserDni parameter and converts it to a list of TransactionDto objects using the transactionConverter.
     * @param appUserDni the dni of the app user whose incoming transactions are to be retrieved.
     * @return a list of TransactionDto objects representing the incoming transactions of the account.
     * @throws ResponseStatusException if the app user is not found or if the account is not found for the given app user.
     */
    @Override
    public List<TransactionDto> getIncomingTransactionsByAccountId(String appUserDni) {
        AppUser appUser = appUserServiceImpl.findAppUserByDni(appUserDni)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el usuario"));

        Account account = accountServiceImpl.findAccountByAppUserId(appUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la cuenta"));

        // Retrieve all transactions for the account or an empty list if not found
        List<Transaction> transactions = transactionRepository.findTransactionsByDestinyAccountIdOrderByTransactionDate(account.getId())
                .orElse(Collections.emptyList());

        // Convert transaction to DTO
        return transactions.stream()
                .map(transactionConverter::convertTransactionEntityToTransactionDto)
                .collect(Collectors.toList());
    }

    /**
     * Issue a transaction from one account to another account based on the issueTransactionDto. The transaction is created and saved in the database.
     * The origin account balance is updated and the destiny account balance is updated.
     * @param appUser the app user who is issuing the transaction (used to retrieve the origin account).
     * @param issueTransactionDto the DTO containing the details of the transaction.
     * @return the created transaction.
     * @throws ResponseStatusException if the destiny account is not found.
     * @throws ResponseStatusException if the amount is invalid or if the origin account is not found.
     * @throws ResponseStatusException if the origin account has insufficient balance.
     */
    @Override
    public Transaction issueTransaction(AppUser appUser, IssueTransactionDto issueTransactionDto) {
        // Retrieve the origin and destination accounts
        Optional<Account> originAccountOpt = accountServiceImpl.findAccountByAppUserId(appUser.getId());
        Optional<Account> destinyAccountOpt = accountServiceImpl.findAccountByAccountNumber(issueTransactionDto.getDestinyAccountNumber());

        // Verify that both accounts exist and the amount is positive
        if (destinyAccountOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cuenta destino no encontrada");
        }

        if (originAccountOpt.isPresent()
                && (!originAccountOpt.get().getAccountNumber().equals(issueTransactionDto.getDestinyAccountNumber()))
                && issueTransactionDto.getAmount() > 0) {

            Account originAccount = originAccountOpt.get();
            Account destinyAccount = destinyAccountOpt.get();
            double amount = issueTransactionDto.getAmount();

            // Check if the origin account has sufficient balance
            if (originAccount.getBalance() < amount) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
            }

            // Perform the transaction: update the account balances
            originAccount.setBalance(originAccount.getBalance() - amount);
            destinyAccount.setBalance(destinyAccount.getBalance() + amount);

            // Create the transaction
            Transaction transaction = Transaction.builder()
                    .amount(amount)
                    .detail(issueTransactionDto.getDetail())
                    .originAccount(originAccount)
                    .destinyAccount(destinyAccount)
                    .build();

            // Save the transaction in the repository and return it
            return transactionRepository.save(transaction);
        } else {
            // Throw an exception if the accounts do not exist or the amount is invalid
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se ha podido realizar la operación");
        }
    }

    /**
     * Issue a Bizum transaction from one account to another account based on the bizumDto. The transaction is created and saved in the database.
     * @param appUser the app user who is issuing the transaction (used to retrieve the origin account).
     * @param bizumDto the DTO containing the details of the transaction.
     * @throws ResponseStatusException if the origin account has insufficient balance.
     * @throws ResponseStatusException if the amount is invalid or if the origin account is not found.
     */
    @Override
    public void issueBizum(AppUser appUser, BizumDto bizumDto) {
        // Retrieve the origin and destination accounts based on the account numbers in the DTO
        Optional<Account> originAccountOpt = accountServiceImpl.findAccountByAppUserId(appUser.getId());

        if (originAccountOpt.isPresent() && bizumDto.getAmount() > 0) {
            Account originAccount = originAccountOpt.get();
            double amount = bizumDto.getAmount();

            // Check if the origin account has sufficient balance
            if (originAccount.getBalance() < amount) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
            }

            // Perform the bizum transaction: update the account balance
            originAccount.setBalance(originAccount.getBalance() - amount);

            Transaction transaction = Transaction.builder()
                    .amount(amount)
                    .detail(bizumDto.getDetail())
                    .originAccount(originAccount)
                    .destinyAccount(null)
                    .build();
            // Update the account and save the transaction
            //accountRepository.save(originAccount);
            transactionRepository.save(transaction);
        } else {
            // Throw an exception if the accounts do not exist or the amount is invalid
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se ha podido realizar la operación");
        }
    }
}
