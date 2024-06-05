package com.dicaro.dicarobank;

import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.AppUserAuthorization;
import com.dicaro.dicarobank.repository.AccountRepository;
import com.dicaro.dicarobank.repository.AppUserRepository;
import com.dicaro.dicarobank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class DicaroBankApp implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(DicaroBankApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (appUserRepository.count() == 0 && accountRepository.count() == 0 && transactionRepository.count() == 0) {

            // Generate 5 AppUsers
            AppUser user1 = AppUser.builder()
                    .dni("23456789D")
                    .name("Juan")
                    .surname("Pérez")
                    .phone("654987321")
                    .email("juan@example.com")
                    .password(passwordEncoder.encode("user1"))
                    .authorities(Stream.of(AppUserAuthorization.USER).toList())
                    .build();
            appUserRepository.save(user1);

            AppUser user2 = AppUser.builder()
                    .dni("98765432M")
                    .name("María")
                    .surname("García")
                    .phone("654321898")
                    .email("maria@example.com")
                    .password(passwordEncoder.encode("user2"))
                    .authorities(Stream.of(AppUserAuthorization.USER).toList())
                    .build();
            appUserRepository.save(user2);

            AppUser user3 = AppUser.builder()
                    .dni("45678901G")
                    .name("Pedro")
                    .surname("López")
                    .phone("741852963")
                    .email("pedro@example.com")
                    .password(passwordEncoder.encode("user3"))
                    .authorities(Stream.of(AppUserAuthorization.USER).toList())
                    .build();
            appUserRepository.save(user3);

            AppUser user4 = AppUser.builder()
                    .dni("21098765Z")
                    .name("Ana")
                    .surname("Rodríguez")
                    .phone("693582471")
                    .email("ana@example.com")
                    .password(passwordEncoder.encode("user4"))
                    .authorities(Stream.of(AppUserAuthorization.USER).toList())
                    .build();
            appUserRepository.save(user4);

            AppUser user5 = AppUser.builder()
                    .dni("54321098C")
                    .name("Laura")
                    .surname("Martínez")
                    .phone("687412359")
                    .email("laura@example.com")
                    .password(passwordEncoder.encode("user5"))
                    .authorities(Stream.of(AppUserAuthorization.USER).toList())
                    .build();
            appUserRepository.save(user5);

            Account account1 = new Account();
            account1.setBalance(2500); // Saldo inicial
            account1.setAppUser(user1);
            accountRepository.save(account1);

            Account account2 = new Account();
            account2.setBalance(1000); // Saldo inicial
            account2.setAppUser(user2);
            accountRepository.save(account2);

            Account account3 = new Account();
            account3.setBalance(800); // Saldo inicial
            account3.setAppUser(user3);
            accountRepository.save(account3);

            Account account4 = new Account();
            account4.setBalance(36000); // Saldo inicial
            account4.setAppUser(user4);
            accountRepository.save(account4);

            Account account5 = new Account();
            account5.setBalance(13000); // Saldo inicial
            account5.setAppUser(user5);
            accountRepository.save(account5);

            /*// Creación de 10 Transactions para account1
            List<Transaction> transactions1 = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Transaction transaction = new Transaction();
                transaction.setAmount(Math.floor(Math.random() * 10000)); // Monto aleatorio entre 0 y 500
                transaction.setDetail("Transaction " + i);
                transaction.setOriginAccount(account1);
                transaction.setDestinyAccount(account3);
                transactions1.add(transaction);
            }
            transactionRepository.saveAll(transactions1);

            // Creación de 10 Transactions para account2
            List<Transaction> transactions2 = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Transaction transaction = new Transaction();
                transaction.setAmount(Math.floor(Math.random() * 10000)); // Monto aleatorio entre 0 y 500
                transaction.setDetail("Transaction " + i);
                transaction.setOriginAccount(account2);
                transaction.setDestinyAccount(account1);
                transactions2.add(transaction);
            }
            transactionRepository.saveAll(transactions2);

            // Creación de 10 Transactions para account3
            List<Transaction> transactions3 = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Transaction transaction = new Transaction();
                transaction.setAmount(Math.floor(Math.random() * 10000)); // Monto aleatorio entre 0 y 500
                transaction.setDetail("Transaction " + i);
                transaction.setOriginAccount(account3);
                transaction.setDestinyAccount(account2);
                transactions3.add(transaction);
            }
            transactionRepository.saveAll(transactions3);

            // Creación de 10 Transactions para account4
            List<Transaction> transactions4 = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Transaction transaction = new Transaction();
                transaction.setAmount(Math.floor(Math.random() * 10000)); // Monto aleatorio entre 0 y 500
                transaction.setDetail("Transaction " + i);
                transaction.setOriginAccount(account4);
                transaction.setDestinyAccount(account1);
                transactions4.add(transaction);
            }
            transactionRepository.saveAll(transactions4);

            // Creación de 10 Transactions para account5
            List<Transaction> transactions5 = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Transaction transaction = new Transaction();
                transaction.setAmount(Math.floor(Math.random() * 10000)); // Monto aleatorio entre 0 y 500
                transaction.setDetail("Transaction " + i);
                transaction.setOriginAccount(account5);
                transaction.setDestinyAccount(account4);
                transactions5.add(transaction);
            }
            transactionRepository.saveAll(transactions5);*/
        } else {
            System.out.println("Database already populated");
        }
    }
}
