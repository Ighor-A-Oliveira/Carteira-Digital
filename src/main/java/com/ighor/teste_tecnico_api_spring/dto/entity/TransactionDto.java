package com.ighor.teste_tecnico_api_spring.dto.entity;

import com.ighor.teste_tecnico_api_spring.entity.Account;
import com.ighor.teste_tecnico_api_spring.entity.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    @NotBlank
    private TransactionType type; // DEPOSIT, WITHDRAW, TRANSFER_INTERNAL, TRANSFER_EXTERNAL
    @NotBlank
    private BigDecimal amount;
    @NotBlank
    private Long sourceAccountId;
    @Nullable
    private Long destinationAccountId; // pode ser null
    @Nullable
    private Long externalDestinationAccountNumber;
    @NotBlank
    private OffsetDateTime timestamp;
    @NotBlank
    private BigDecimal balanceAfterOperation;
}
