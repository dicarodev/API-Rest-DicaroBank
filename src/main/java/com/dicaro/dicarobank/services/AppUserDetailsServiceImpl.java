package com.dicaro.dicarobank.services;

import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
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

    private final AppUserServiceImpl appUserServiceImpl;

    /**
     * Load a user by its dni and return it as a UserDetails model.
     * @param dni the username identifying the user whose data is required.
     * @return the UserDetails model that contains the user's data.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        return appUserServiceImpl
                .findAppUserByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));
    }
}
