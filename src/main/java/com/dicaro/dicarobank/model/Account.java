package com.dicaro.dicarobank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model for account
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Builder.Default
    private String accountNumber = String.format("ES%014d", new Random().nextLong(Math.abs(System.currentTimeMillis())));

    private double balance;

    @OneToOne()
    private AppUser appUser;

    @OneToMany(mappedBy = "originAccount")
    @JsonManagedReference
    private List<Transaction> originTransactionsList = new ArrayList<>();

    @OneToMany(mappedBy = "destinyAccount")
    @JsonManagedReference
    private List<Transaction> destinyTransactionsList = new ArrayList<>();
}
