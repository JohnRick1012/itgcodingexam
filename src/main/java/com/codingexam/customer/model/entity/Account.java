package com.codingexam.customer.model.entity;

import com.codingexam.customer.commons.AccountType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(name = "account_number", nullable = false, unique = true)
    private Long accountNumber;
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "available_balance")
    private Double availableBalance;
}
