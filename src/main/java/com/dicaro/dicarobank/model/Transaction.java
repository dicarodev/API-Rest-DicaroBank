package com.dicaro.dicarobank.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private LocalDate transactionDate;

    private double amount;
    private String detail;

    @ManyToOne()
    private Account originAccount;

    @ManyToOne()
    private Account destinyAccount;
}
