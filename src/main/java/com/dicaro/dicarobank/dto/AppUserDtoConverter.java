package com.dicaro.dicarobank.dto;

import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.AppUserAuthorization;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/*
* Convert AppUser to AppUserDto
*/
@Component
public class AppUserDtoConverter {

    // Convert AppUser to GetAppUserDto (only name and authorities) for response body
    public GetAppUserDto convertAppUserEntityToGetAppUserDto(AppUser appUser){
        return GetAppUserDto.builder()
                .name(appUser.getName())
                .authorities(appUser.getAuthorities().stream()
                        .map(AppUserAuthorization::name)
                        .collect(Collectors.toSet()))
                .build();
    }
}
