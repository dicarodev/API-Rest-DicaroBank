package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.model.AppUserAdapter;
import com.dicaro.dicarobank.services.AppUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserServiceImpl appUserServiceImpl;

    /*
     * Method to delete an app user by its dni in the database when the user is authenticated.
     */
    @DeleteMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal AppUserAdapter appUserAdapter){
        appUserServiceImpl.deleteAppUser(appUserAdapter);
        return ResponseEntity.ok().build();
    }
}
