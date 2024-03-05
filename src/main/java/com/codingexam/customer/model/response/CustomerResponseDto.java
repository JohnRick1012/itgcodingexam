package com.codingexam.customer.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponseDto {

    private Long customerNumber;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;
    private List<AccountResponseDto> savings;
    private int transactionStatusCode;
    private String transactionStatusDescription;

}
