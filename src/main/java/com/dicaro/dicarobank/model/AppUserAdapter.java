package com.dicaro.dicarobank.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
 * This class is used to adapt the AppUser model to the UserDetails interface.
 */
@RequiredArgsConstructor
public class AppUserAdapter implements UserDetails {

    private final AppUser user;

    // Get authorities from the AppUser model and return them as a Collection of GrantedAuthority models.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities()
                .stream()
                .map(authorities -> new SimpleGrantedAuthority("ROLE_" + authorities.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getDni();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
