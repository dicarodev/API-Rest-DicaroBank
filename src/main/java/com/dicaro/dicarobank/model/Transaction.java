package com.dicaro.dicarobank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Model for transaction
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime transactionDate = LocalDateTime.now();

    private double amount;
    private String detail;

    @ManyToOne()
    @JsonBackReference
    private Account originAccount;

    @ManyToOne()
    @JsonBackReference
    private Account destinyAccount;
}
