package com.dicaro.dicarobank.services.AppUser;

import com.dicaro.dicarobank.dto.SingUpAppUserDto;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.AppUserAdapter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AppUserService {
    Optional<AppUser> findAppUserByDni(String dni);
    AppUser singUpAppUser(SingUpAppUserDto singUpAppUserDto);
    void deleteAppUser(AppUserAdapter appUserAdapter);
}
