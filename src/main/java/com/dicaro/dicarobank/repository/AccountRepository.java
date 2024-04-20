package com.dicaro.dicarobank.repository;

import com.dicaro.dicarobank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for account
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAppUser_Id(Long id);
}
