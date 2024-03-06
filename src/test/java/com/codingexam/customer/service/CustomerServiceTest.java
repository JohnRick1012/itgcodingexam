package com.codingexam.customer.service;

import com.codingexam.customer.model.entity.Customer;
import com.codingexam.customer.model.response.AccountResponseDto;
import com.codingexam.customer.model.response.CustomerResponse;
import com.codingexam.customer.model.response.CustomerResponseDto;
import com.codingexam.customer.repository.CustomerRepository;
import com.codingexam.customer.service.impl.CustomerServiceImpl;
import com.codingexam.customer.utility.TestObjectBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;

    @Test
    void saveCustomerAccount_ok() {
        Customer mockRequest = TestObjectBuilder.buildRequest("testJson/CustomerRequest_ok.txt", Customer.class);
        CustomerResponse mockResponse = CustomerResponse.builder()
                        .customerNumber(2403060L)
                        .transactionStatusCode(HttpStatus.CREATED.value())
                        .transactionStatusDescription("Customer account created")
                        .build();
        when(customerRepository.save(mockRequest)).thenReturn(mockRequest);

        CustomerResponse actual = customerService.saveCustomerAccount(mockRequest);

        assertNotNull(actual);
    }

    @Test
    void saveCustomerAccount_fail() {
        Customer mockRequest = TestObjectBuilder.buildRequest("testJson/CustomerRequest_fail.txt", Customer.class);
        CustomerResponse mockResponse = CustomerResponse.builder()
                .customerNumber(2403060L)
                .transactionStatusCode(HttpStatus.NOT_FOUND.value())
                .transactionStatusDescription("Email is required")
                .build();

        when(customerRepository.save(mockRequest)).thenReturn(mockRequest);

        CustomerResponse actual = customerService.saveCustomerAccount(mockRequest);

        assertNotNull(actual);
    }

    @Test
    void getCustomerDetails_ok() {
        Customer mockRequest = TestObjectBuilder.buildRequest("testJson/CustomerRequest_fail.txt", Customer.class);

        when(customerRepository.findByCustomerNumber(Mockito.any())).thenReturn(mockRequest);

        CustomerResponseDto responseDTO = new CustomerResponseDto();
        List<AccountResponseDto> accountResponseDtos = mockRequest.getAccount().stream()
                .map(account -> new AccountResponseDto(account.getAccountNumber(), account.getAccountType(), account.getAvailableBalance()))
                .collect(Collectors.toList());
        responseDTO.setCustomerNumber(2403060L);
        responseDTO.setCustomerName(mockRequest.getCustomerName());
        responseDTO.setCustomerMobile(mockRequest.getCustomerMobile());
        responseDTO.setCustomerEmail(mockRequest.getCustomerEmail());
        responseDTO.setAddress1(mockRequest.getAddress1());
        responseDTO.setAddress2(mockRequest.getAddress2());

        responseDTO.setSavings(accountResponseDtos);

        responseDTO.setTransactionStatusCode(HttpStatus.FOUND.value());
        responseDTO.setTransactionStatusDescription("Customer Account found");

        CustomerResponseDto actual = customerService.getCustomerDetails(mockRequest.getCustomerNumber());

        assertNotNull(actual);
    }

    @Test
    void getCustomerDetails_NotFound() {
        Customer mockRequest = TestObjectBuilder.buildRequest("testJson/CustomerRequest_fail.txt", Customer.class);
        when(customerRepository.findByCustomerNumber(Mockito.any())).thenReturn(null);

        CustomerResponseDto responseDTO = new CustomerResponseDto();

        responseDTO.setTransactionStatusCode(HttpStatus.NOT_FOUND.value());
        responseDTO.setTransactionStatusDescription("Customer not found");
        CustomerResponseDto actual = customerService.getCustomerDetails(2403061L);
        assertNotNull(actual);

    }
}
