package com.dicaro.dicarobank.dto;

import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.AppUserAuthorization;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * AppUserDtoConverter
 */
@Component
public class AppUserDtoConverter {

    /**
     * Convert AppUser to LogInResponseDto (only name and authorities) for response body
     * @param appUser
     * @return LogInResponseDto
     */
    public LogInResponseDto convertAppUserEntityToLogInResponseDto(AppUser appUser){
        return LogInResponseDto.builder()
                .dni(appUser.getDni())
                .authorities(appUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
