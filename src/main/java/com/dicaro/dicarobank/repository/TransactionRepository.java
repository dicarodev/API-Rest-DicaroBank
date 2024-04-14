package com.dicaro.dicarobank.repository;

import com.dicaro.dicarobank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
