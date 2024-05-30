package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.appUser.AppUserConverter;
import com.dicaro.dicarobank.dto.appUser.LogInRequestDto;
import com.dicaro.dicarobank.dto.appUser.LogInResponseDto;
import com.dicaro.dicarobank.dto.appUser.SingUpAppUserDto;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.security.JwtTokeProvider;
import com.dicaro.dicarobank.services.account.AccountServiceImpl;
import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for authentication
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserServiceImpl appUserServiceImpl;
    private final AuthenticationManager authManager;
    private final JwtTokeProvider jwtTokenProvider;
    private final AppUserConverter appUserConverter;
    private final AccountServiceImpl accountServiceImpl;

    /**
     * Controller to sign up an app user and return it as a LogInResponseDto model to the client.
     *
     * @param singUpAppUserDto the singed up app user
     * @return logInResponseDto (only dni and authorities)
     */
    @PostMapping("/singup")
    public ResponseEntity<?> singUpAppUser(@RequestBody SingUpAppUserDto singUpAppUserDto) {
        try {
            AppUser appUser = appUserServiceImpl.singUpAppUser(singUpAppUserDto);
            accountServiceImpl.createNewAccount(appUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }

    /**
     * Controller to log in an app user and return it as a LogInResponseDto model to the client.
     * @param logInRequestDto the log in app user
     * @return logInResponseDto (dni, authorities and JWT token)
     */
    @PostMapping("/login")
    public ResponseEntity<?> logInAppUser(@RequestBody LogInRequestDto logInRequestDto) {
        try {
            Authentication authDTO = new UsernamePasswordAuthenticationToken(logInRequestDto.getDni(), logInRequestDto.getPassword());
            Authentication authentication = authManager.authenticate(authDTO);
            AppUser appUser = (AppUser) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(authentication);

            LogInResponseDto logInResponseDto = new LogInResponseDto(
                    appUser.getName(),
                    appUser.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).toList(),
                    token);

            return ResponseEntity.ok(logInResponseDto);

        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado");
        }
    }

    //Logout not implemented TODO

}
