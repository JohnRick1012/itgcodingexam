package com.codingexam.customer.model.response;

import com.codingexam.customer.commons.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
public class AccountResponseDto {
    private Long accountNumber;
    private String accountType;
    private double availableBalance;

    public AccountResponseDto(Long accountNumber, AccountType accountType, Double availableBalance) {
        this.accountNumber = accountNumber;
        this.accountType = String.valueOf(accountType);
        this.availableBalance = availableBalance;
    }
}
