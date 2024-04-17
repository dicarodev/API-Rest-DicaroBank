package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.AppUserDtoConverter;
import com.dicaro.dicarobank.dto.LogInRequestDto;
import com.dicaro.dicarobank.dto.LogInResponseDto;
import com.dicaro.dicarobank.dto.SingUpAppUserDto;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.security.JwtTokeProvider;
import com.dicaro.dicarobank.services.Account.AccountServiceImpl;
import com.dicaro.dicarobank.services.AppUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserServiceImpl appUserServiceImpl;
    private final AccountServiceImpl accountService;
    private final AuthenticationManager authManager;
    private final JwtTokeProvider jwtTokeProvider;
    private final AppUserDtoConverter appUserDtoConverter;

    /**
     * Controller to sign up an app user and return it as a LogInResponseDto model to the client.
     *
     * @param singUpAppUserDto the singed up app user
     * @return logInResponseDto (only dni and authorities)
     */
    @PostMapping("/singup")
    public ResponseEntity<?> singUpAppUser(@RequestBody SingUpAppUserDto singUpAppUserDto) {
        AppUser appUser = appUserServiceImpl.singUpAppUser(singUpAppUserDto);
        if (appUser != null) {
            accountService.createNewAccount(appUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Controller to log in an app user and return it as a LogInResponseDto model to the client.
     * @param logInRequestDto the log in app user
     * @return logInResponseDto (dni, authorities and JWT token)
     */
    @PostMapping("/login")
    public LogInResponseDto logInAppUser(@RequestBody LogInRequestDto logInRequestDto) {
        Authentication authDTO = new UsernamePasswordAuthenticationToken(logInRequestDto.getDni(), logInRequestDto.getPassword());

        Authentication authentication = authManager.authenticate(authDTO);
        AppUser appUser = (AppUser) authentication.getPrincipal();

        String token = jwtTokeProvider.generateToken(authentication);

        return new LogInResponseDto(
                appUser.getUsername(),
                appUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList(),
                token);
    }

}
