package com.dicaro.dicarobank.services.AppUser;

import com.dicaro.dicarobank.dto.SingInAppUserDto;
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
 * Service for AppUser
 */
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to find an app user by its dni in the database and return it.
     * @return Optional<AppUser>
     */
    @Override
    public Optional<AppUser> findAppUserByDni(String dni) {
        try {
            return repository.findAppUserByDni(dni);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe");
        }
    }

    /**
     * Method to create a new app user by passing a SingInAppUserDto model in the database and return it.
     * @param singInAppUserDto
     * @return AppUser
     */
    @Override
    public AppUser singUpAppUser(SingInAppUserDto singInAppUserDto) {
        AppUser appUser = AppUser.builder()
                .dni(singInAppUserDto.getDni())
                .name(singInAppUserDto.getName())
                .surname(singInAppUserDto.getSurname())
                .phone(singInAppUserDto.getPhone())
                .email(singInAppUserDto.getEmail())
                .password(passwordEncoder.encode(singInAppUserDto.getPassword()))
                .authorities(Stream.of(AppUserAuthorization.USER).toList())
                .build();

        // Return the app user created or throw an exception if the app user already exists.
        try {
            return repository.save(appUser);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }
    }

    /**
     * Method to delete an app user by its dni in the database when the user is authenticated.
     */
    @Override
    public void deleteAppUser(String dniAppUserAuth) {
        Optional<AppUser> user = repository.findAppUserByDni(dniAppUserAuth);

        try {
            if (user.isPresent() && user.get().getDni().equals(dniAppUserAuth)) {
                repository.delete(user.get());
            }
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe y no puede ser eliminado");
        }
    }


}
