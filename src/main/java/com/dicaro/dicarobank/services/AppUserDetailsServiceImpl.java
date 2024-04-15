package com.dicaro.dicarobank.services;

import com.dicaro.dicarobank.services.AppUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service to load a user by its dni and return it as a UserDetails model.
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(AppUserDetailsServiceImpl.class);

    private final AppUserServiceImpl appUserServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        return appUserServiceImpl
                .findAppUserByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));
    }
}
