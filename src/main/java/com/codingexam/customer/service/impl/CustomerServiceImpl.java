package com.codingexam.customer.service.impl;

import com.codingexam.customer.commons.AccountType;
import com.codingexam.customer.model.entity.Account;
import com.codingexam.customer.model.entity.Customer;
import com.codingexam.customer.model.response.AccountResponseDto;
import com.codingexam.customer.model.response.CustomerResponse;
import com.codingexam.customer.model.response.CustomerResponseDto;
import com.codingexam.customer.repository.CustomerRepository;
import com.codingexam.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository  customerRepository;

    @Override
    public CustomerResponse saveCustomerAccount(Customer customer) {
        log.info("Customer: {}", customer);


        String pattern = "yyMMdd";
        AtomicLong atomicLong = new AtomicLong(10000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Long customerNumber = Long.parseLong(simpleDateFormat.format(new Date()) + customer.getId());
        customer.setCustomerNumber(customerNumber);

        Account account = new Account();
        Long accountNumber = Long.parseLong(simpleDateFormat.format(new Date()) + account.getId());

        List<Account> accounts = new ArrayList<>();
        account.setAccountNumber(atomicLong.incrementAndGet());
        account.setAccountType(AccountType.SAVINGS);
        account.setAvailableBalance(0.0);
        account.setCustomer(customer);
        accounts.add(account);
        customer.setAccount(accounts);

        Customer saveCostumer = customerRepository.save(customer);

        if(customer.getCustomerEmail() == null) {
            return CustomerResponse.builder()
                    .transactionStatusCode(HttpStatus.NOT_FOUND.value())
                    .transactionStatusDescription("Email is required")
                    .build();
        } else{
            return CustomerResponse.builder()
                    .customerNumber(customer.getCustomerNumber())
                    .transactionStatusCode(HttpStatus.CREATED.value())
                    .transactionStatusDescription("Customer account created")
                    .build();
        }
    }

    @Override
    public CustomerResponseDto getCustomerDetails(Long customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber);
        CustomerResponseDto responseDTO = new CustomerResponseDto();
        if(customer == null) {
            responseDTO.setTransactionStatusCode(HttpStatus.NOT_FOUND.value());
            responseDTO.setTransactionStatusDescription("Customer not found");
            return responseDTO;
        } else {
            List<AccountResponseDto> accountResponseDtos = customer.getAccount().stream()
                    .map(account -> new AccountResponseDto(account.getAccountNumber(), account.getAccountType(), account.getAvailableBalance()))
                    .collect(Collectors.toList());
            responseDTO.setCustomerNumber(customer.getCustomerNumber());
            responseDTO.setCustomerName(customer.getCustomerName());
            responseDTO.setCustomerMobile(customer.getCustomerMobile());
            responseDTO.setCustomerEmail(customer.getCustomerEmail());
            responseDTO.setAddress1(customer.getAddress1());
            responseDTO.setAddress2(customer.getAddress2());

            responseDTO.setSavings(accountResponseDtos);

            responseDTO.setTransactionStatusCode(HttpStatus.FOUND.value());
            responseDTO.setTransactionStatusDescription("Customer Account found");

            return responseDTO;
        }
    }
}
