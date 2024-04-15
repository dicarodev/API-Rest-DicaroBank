package com.dicaro.dicarobank.repository;

import com.dicaro.dicarobank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for account
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
