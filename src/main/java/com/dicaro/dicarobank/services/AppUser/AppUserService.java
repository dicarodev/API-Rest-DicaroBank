package com.dicaro.dicarobank.services.AppUser;

import com.dicaro.dicarobank.dto.SingUpAppUserDto;
import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;

import java.util.Optional;

/**
 * Interface for app user service
 */
public interface AppUserService {
    Optional<AppUser> findAppUserByDni(String dni);
    AppUser singUpAppUser(SingUpAppUserDto singUpAppUserDto);
    void deleteAppUser(String appUserDniAuth);
}
