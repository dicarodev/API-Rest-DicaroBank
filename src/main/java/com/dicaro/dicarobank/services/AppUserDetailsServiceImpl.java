package com.dicaro.dicarobank.services;

import com.dicaro.dicarobank.model.AppUserAdapter;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.repository.AppUserRepository;
import com.dicaro.dicarobank.services.AppUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Service to load a user by its dni and return it as a UserDetails model.
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final AppUserServiceImpl appUserServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        AppUser user = appUserServiceImpl
                .findAppUserByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

        return new AppUserAdapter(user);
    }
}
