package com.dicaro.dicarobank;

import com.dicaro.dicarobank.model.Account;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.AppUserAuthorization;
import com.dicaro.dicarobank.model.Transaction;
import com.dicaro.dicarobank.repository.AccountRepository;
import com.dicaro.dicarobank.repository.AppUserRepository;
import com.dicaro.dicarobank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
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

            // Generate 5 Accounts
            Account account1 = new Account();
            account1.setBalance(2500);
            account1.setAppUser(user1);
            accountRepository.save(account1);

            Account account2 = new Account();
            account2.setBalance(1000);
            account2.setAppUser(user2);
            accountRepository.save(account2);

            Account account3 = new Account();
            account3.setBalance(800);
            account3.setAppUser(user3);
            accountRepository.save(account3);

            Account account4 = new Account();
            account4.setBalance(36000);
            account4.setAppUser(user4);
            accountRepository.save(account4);

            Account account5 = new Account();
            account5.setBalance(13000);
            account5.setAppUser(user5);
            accountRepository.save(account5);

            // Generate 20 Transactions
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                createIncomingTransaction(account1, account2, random);
                createIncomingTransaction(account2, account3, random);
                createIncomingTransaction(account3, account4, random);
                createIncomingTransaction(account4, account5, random);
                createIncomingTransaction(account5, account1, random);

                createOutgoingTransaction(account1, account2, random);
                createOutgoingTransaction(account2, account3, random);
                createOutgoingTransaction(account3, account4, random);
                createOutgoingTransaction(account4, account5, random);
                createOutgoingTransaction(account5, account1, random);
            }

        } else {
            System.out.println("Database already populated");
        }
    }

    // Helper methods to create transactions between accounts and save them in the database
    private void createIncomingTransaction(Account originAccount, Account destinyAccount, Random random) {
        double amount = 50 + (500 - 50) * random.nextDouble();
        String[] incomingDetails = {
                "Depósito",
                "Transferencia recibida",
                "Reembolso",
                "Pago recibido",
                "Inversión recibida",
                "Cobro de servicios",
                "Venta de productos",
                "Premio",
                "Intereses",
                "Pago de clientes",
                "Regalo recibido",
                "Bono",
                "Devolución de impuestos",
                "Remesa familiar",
                "Herencia",
                "Lotería ganada",
                "Ingreso por freelance",
                "Venta de acciones",
                "Reembolso por devolución",
                "Incentivo de empresa"
        };

        String detail = incomingDetails[random.nextInt(incomingDetails.length)];

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .detail(detail)
                .originAccount(destinyAccount)
                .destinyAccount(originAccount)
                .build();
        transactionRepository.save(transaction);
    }
    private void createOutgoingTransaction(Account originAccount, Account destinyAccount, Random random) {
        double amount = 50 + (500 - 50) * random.nextDouble();
        String[] outgoingDetails = {
                "Pago de servicios",
                "Compra en supermercado",
                "Pago de alquiler",
                "Transferencia entre cuentas",
                "Compra online",
                "Pago de tarjeta de crédito",
                "Gastos varios",
                "Suscripción",
                "Donación",
                "Multa",
                "Compra de ropa",
                "Gastos médicos",
                "Pago de impuestos",
                "Mantenimiento del coche",
                "Pago de facturas",
                "Entretenimiento",
                "Cena en restaurante",
                "Viaje",
                "Educación",
                "Regalo"
        };

        String detail = outgoingDetails[random.nextInt(outgoingDetails.length)];

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .detail(detail)
                .originAccount(originAccount)
                .destinyAccount(destinyAccount)
                .build();
        transactionRepository.save(transaction);
    }
}
