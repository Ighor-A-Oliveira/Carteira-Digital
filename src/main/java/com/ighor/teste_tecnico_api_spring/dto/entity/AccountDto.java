package com.ighor.teste_tecnico_api_spring.dto.entity;

import com.ighor.teste_tecnico_api_spring.entity.Account;
import com.ighor.teste_tecnico_api_spring.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {

    private Long accountId;
    private Long accountNumber;
    private Long ownerId;
    private BigDecimal balance ;
    public Account.AccountStatus status;
    private OffsetDateTime createdAt;

    public AccountDto(Long accountNumber, BigDecimal balance, OffsetDateTime createdAt, Account.AccountStatus status){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = createdAt;
        this.status = status;
    }
}
