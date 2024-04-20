package com.dicaro.dicarobank.repository;

import com.dicaro.dicarobank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for transactions
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findTransactionsByOriginAccountIdOrderByTransactionDate(Long id);
}
