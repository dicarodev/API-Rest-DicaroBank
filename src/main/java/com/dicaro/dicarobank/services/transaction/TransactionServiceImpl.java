package com.dicaro.dicarobank.services.transaction;

import com.dicaro.dicarobank.dto.IssueTransactionDto;
import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.Transaction;
import com.dicaro.dicarobank.repository.TransactionRepository;
import com.dicaro.dicarobank.services.account.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceImpl accountServiceImpl;
    @Override
    public Optional<List<Transaction>> getOutgoingTransactionsByAccountId(Long id) {
        return transactionRepository.findTransactionsByOriginAccountIdOrderByTransactionDate(id);
    }

    @Override
    public Optional<List<Transaction>> getIncomingTransactionsByAccountId(Long id) {
        return transactionRepository.findTransactionsByDestinyAccountIdOrderByTransactionDate(id);
    }

    @Override
    public Transaction issueTransaction(AppUser appUser, IssueTransactionDto issueTransactionDto) {
        // Retrieve the origin and destination accounts based on the account numbers in the DTO
        Optional<Account> originAccountOpt = accountServiceImpl.findAccountByAppUserId(appUser.getId());
        Optional<Account> destinyAccountOpt = accountServiceImpl.findAccountByAccountNumber(issueTransactionDto.getDestinyAccountNumber());

        // Verify that both accounts exist and the amount is positive
        if (destinyAccountOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cuenta destino no encontrada");
        }

        if (originAccountOpt.isPresent() && issueTransactionDto.getAmount() > 0) {
            Account originAccount = originAccountOpt.get();
            Account destinyAccount = destinyAccountOpt.get();
            double amount = issueTransactionDto.getAmount();

            // Check if the origin account has sufficient balance
            if (originAccount.getBalance() < amount) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede realizar la operación, saldo insuficiente");
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
}
