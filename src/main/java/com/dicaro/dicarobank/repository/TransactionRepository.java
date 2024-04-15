package com.dicaro.dicarobank.repository;

import com.dicaro.dicarobank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for transactions
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
