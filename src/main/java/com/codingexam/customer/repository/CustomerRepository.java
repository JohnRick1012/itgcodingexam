package com.codingexam.customer.repository;

import com.codingexam.customer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCustomerNumber(Long customerNumber);

}
