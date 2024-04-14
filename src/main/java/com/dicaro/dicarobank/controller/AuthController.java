package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.dto.AppUserDtoConverter;
import com.dicaro.dicarobank.dto.SingUpAppUserDto;
import com.dicaro.dicarobank.services.AppUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserServiceImpl appUserServiceImpl;
    private final AppUserDtoConverter appUserDtoConverter;

    // Controller to create a new app user in the database and return it as a GetAppUserDto model to the client with the status code.
    @PostMapping()
    public ResponseEntity<?> singUpAppUser(@RequestBody SingUpAppUserDto singUpAppUserDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                appUserDtoConverter.convertAppUserEntityToGetAppUserDto(
                        appUserServiceImpl.singUpAppUser(singUpAppUserDto)));
    }

    @GetMapping()
    public String test() {
        return "Access to /test granted";
    }

}
