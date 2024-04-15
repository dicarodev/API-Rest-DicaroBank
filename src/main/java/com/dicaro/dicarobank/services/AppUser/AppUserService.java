package com.dicaro.dicarobank.services.AppUser;

import com.dicaro.dicarobank.dto.SingInAppUserDto;
import com.dicaro.dicarobank.model.AppUser;

import java.util.Optional;

/**
 * Interface for app user service
 */
public interface AppUserService {
    Optional<AppUser> findAppUserByDni(String dni);
    AppUser singUpAppUser(SingInAppUserDto singInAppUserDto);
    void deleteAppUser(AppUser appUser);
}
