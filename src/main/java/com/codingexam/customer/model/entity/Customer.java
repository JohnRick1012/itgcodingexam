package com.codingexam.customer.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_number_generator")
    @SequenceGenerator(name = "customer_number_generator", sequenceName = "customer_number_seq", allocationSize = 1)
    private Long id;
    @Column(name = "customer_number", length = 50, unique = true)
    private Long customerNumber;
    @Column(name = "customer_name", length = 50, nullable = false)
    private String customerName;
    @Column(name = "customer_mobile", length = 20, nullable = false)
    private String customerMobile;
    @Column(name = "customer_email", length = 50)
    private String customerEmail;
    @Column(name = "address_1", length = 100, nullable = false)
    private String address1;
    @Column(name = "address_2", length = 100)
    private String address2;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> account;

}
