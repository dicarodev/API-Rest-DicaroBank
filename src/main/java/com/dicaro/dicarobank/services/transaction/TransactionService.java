package com.dicaro.dicarobank.services.transaction;

import com.dicaro.dicarobank.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Optional<List<Transaction>> getTransactionsByAccountId(Long id);
}
