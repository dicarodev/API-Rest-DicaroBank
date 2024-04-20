package com.dicaro.dicarobank.repository;


import com.dicaro.dicarobank.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for app user
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByDni(String dni);
}
