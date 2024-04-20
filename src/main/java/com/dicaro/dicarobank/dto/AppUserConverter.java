package com.dicaro.dicarobank.dto;

import com.dicaro.dicarobank.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * AppUserConverter
 */
@Component
public class AppUserConverter {

    /**
     * Convert appUser to LogInResponseDto (only name and authorities) for response body
     * @param appUser the app user entity
     * @return LogInResponseDto
     */
    public LogInResponseDto convertAppUserEntityToLogInResponseDto(AppUser appUser){
        return LogInResponseDto.builder()
                .name(appUser.getName())
                .authorities(appUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
