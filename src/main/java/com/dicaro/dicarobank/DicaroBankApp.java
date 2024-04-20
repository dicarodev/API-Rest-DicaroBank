package com.dicaro.dicarobank;

import com.dicaro.dicarobank.repository.AccountRepository;
import com.dicaro.dicarobank.repository.AppUserRepository;
import com.dicaro.dicarobank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        // Generate 5 AppUsers
        /*AppUser user1 = AppUser.builder()
                .dni("12345678")
                .name("Juan")
                .surname("Pérez")
                .phone("98765432")
                .email("juan@example.com")
                .password(passwordEncoder.encode("user1"))
                .build();
        appUserRepository.save(user1);

        AppUser user2 = AppUser.builder()
                .dni("87654321")
                .name("María")
                .surname("García")
                .phone("65432198")
                .email("maria@example.com")
                .password(passwordEncoder.encode("user2"))
                .build();
        appUserRepository.save(user2);

        AppUser user3 = AppUser.builder()
                .dni("13579246")
                .name("Pedro")
                .surname("López")
                .phone("36985214")
                .email("pedro@example.com")
                .password(passwordEncoder.encode("user3"))
                .build();
        appUserRepository.save(user3);

        AppUser user4 = AppUser.builder()
                .dni("24681357")
                .name("Ana")
                .surname("Rodríguez")
                .phone("25814736")
                .email("ana@example.com")
                .password(passwordEncoder.encode("user4"))
                .build();
        appUserRepository.save(user4);

        AppUser user5 = AppUser.builder()
                .dni("98765432")
                .name("Laura")
                .surname("Martínez")
                .phone("14725836")
                .email("laura@example.com")
                .password(passwordEncoder.encode("user5"))
                .build();
        appUserRepository.save(user5);

        // Creación de account para user1
        Account account1 = new Account();
        account1.setBalance(0); // Saldo inicial
        account1.setAppUser(user1);
        accountRepository.save(account1);

        // Creación de account para user2
        Account account2 = new Account();
        account2.setBalance(0); // Saldo inicial
        account2.setAppUser(user2);
        accountRepository.save(account2);

        // Creación de account para user3
        Account account3 = new Account();
        account3.setBalance(0); // Saldo inicial
        account3.setAppUser(user3);
        accountRepository.save(account3);

        // Creación de account para user4
        Account account4 = new Account();
        account4.setBalance(0); // Saldo inicial
        account4.setAppUser(user4);
        accountRepository.save(account4);

        // Creación de account para user5
        Account account5 = new Account();
        account5.setBalance(0); // Saldo inicial
        account5.setAppUser(user5);
        accountRepository.save(account5);

        // Creación de 10 Transactions para account1
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

    }
}
