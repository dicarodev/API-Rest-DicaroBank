package com.dicaro.dicarobank.controller;

import com.dicaro.dicarobank.services.appUser.AppUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for app user
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserServiceImpl appUserServiceImpl;

    /**
     * Method to delete an app user by its dni in the database when the user is authenticated.
     * @param dniAppUserAuth the dni of the app user authenticated
     * @return ResponseEntity with an empty body 200
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal String dniAppUserAuth) {
        appUserServiceImpl.deleteAppUser(dniAppUserAuth);
        return ResponseEntity.ok().body("Usuario eliminado con éxito");
    }
}
