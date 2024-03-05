package com.codingexam.customer.controller;

import com.codingexam.customer.model.entity.Customer;
import com.codingexam.customer.model.response.CustomerResponse;
import com.codingexam.customer.model.response.CustomerResponseDto;
import com.codingexam.customer.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> saveCustomerAccount(
            @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.saveCustomerAccount(customer), HttpStatus.CREATED);
    }

    @GetMapping(value = "/account/{customerNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> getCustomerDetails(
            @PathVariable Long customerNumber) {
        return ResponseEntity.ok(customerService.getCustomerDetails(customerNumber));
    }
}
