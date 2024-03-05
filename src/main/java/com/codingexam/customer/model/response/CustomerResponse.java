package com.codingexam.customer.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerResponse {

    private Long customerNumber;
    private int transactionStatusCode;
    private String transactionStatusDescription;

}
