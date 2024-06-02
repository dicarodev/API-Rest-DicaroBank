package com.dicaro.dicarobank.services.appUser;

import com.dicaro.dicarobank.dto.appUser.SingUpAppUserDto;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.AppUserAuthorization;
import com.dicaro.dicarobank.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service for appUser
 */
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Find appUser by dni in the database and return it. If the user does not exist, it will throw an exception with a 404 status.
     * @param dni the dni of the appUser to find in the database.
     * @return Optional<AppUser> the appUser found or an empty Optional if the appUser does not exist.
     * @throws ResponseStatusException if the user does not exist
     */
    @Override
    public Optional<AppUser> findAppUserByDni(String dni) {
        try {
            return appUserRepository.findAppUserByDni(dni);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe");
        }
    }

    /**
     * Method to create an app user in the database when the user signs up. If the user already exists, it will throw an exception with a 400 status.
     * @param singUpAppUserDto the dto to create the app user.
     * @return the created app user.
     * @throws ResponseStatusException if the user already exists or if there was a problem creating the app user.
     */
    @Override
    public AppUser singUpAppUser(SingUpAppUserDto singUpAppUserDto) {
        try {
            AppUser appUser = AppUser.builder()
                    .dni(singUpAppUserDto.getDni())
                    .name(singUpAppUserDto.getName())
                    .surname(singUpAppUserDto.getSurname())
                    .phone(singUpAppUserDto.getPhone())
                    .email(singUpAppUserDto.getEmail())
                    .password(passwordEncoder.encode(singUpAppUserDto.getPassword()))
                    .authorities(Stream.of(AppUserAuthorization.USER).toList())
                    .build();

            // Return the app user created or throw an exception if the app user already exists.
            return appUserRepository.save(appUser);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }
    }

    /**
     * Delete an app user from the database by its dni. If the user does not exist, it will throw an exception with a 404 status.
     * @param dniAppUserAuth the dni of the app user to delete.
     * @throws ResponseStatusException if the user does not exist
     */
    @Override
    public void deleteAppUser(String dniAppUserAuth) {
        try {
            Optional<AppUser> user = appUserRepository.findAppUserByDni(dniAppUserAuth);

            if (user.isPresent() && user.get().getDni().equals(dniAppUserAuth)) {
                appUserRepository.delete(user.get());
            }
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe y no puede ser eliminado");
        }
    }


}
