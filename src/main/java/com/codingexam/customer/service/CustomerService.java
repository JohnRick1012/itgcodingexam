package com.codingexam.customer.service;

import com.codingexam.customer.model.entity.Customer;
import com.codingexam.customer.model.response.CustomerResponse;
import com.codingexam.customer.model.response.CustomerResponseDto;

public interface CustomerService {

    CustomerResponse saveCustomerAccount(Customer customer);
    CustomerResponseDto getCustomerDetails(Long customerNumber);

}
