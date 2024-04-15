package com.dicaro.dicarobank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private int accountNumber;

    private double balance;

    @ManyToOne()
    private AppUser appUser;

    @OneToMany(mappedBy = "originAccount")
    private List<Transaction> originTransactionsList = new ArrayList<>();

    @OneToMany(mappedBy = "destinyAccount")
    private List<Transaction> destinyTransactionsList = new ArrayList<>();
}
