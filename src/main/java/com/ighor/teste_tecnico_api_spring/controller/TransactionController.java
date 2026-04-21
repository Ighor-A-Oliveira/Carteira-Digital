package com.ighor.teste_tecnico_api_spring.controller;

import com.ighor.teste_tecnico_api_spring.dto.entity.TransactionDto;
import com.ighor.teste_tecnico_api_spring.dto.request.DepositRequest;
import com.ighor.teste_tecnico_api_spring.dto.request.ExternalTransferRequest;
import com.ighor.teste_tecnico_api_spring.dto.request.InternalTransferRequest;
import com.ighor.teste_tecnico_api_spring.dto.request.WithdrawRequest;
import com.ighor.teste_tecnico_api_spring.dto.response.TransactionResponse;
import com.ighor.teste_tecnico_api_spring.entity.Transaction;
import com.ighor.teste_tecnico_api_spring.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService= transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest depositRequest){
        //Criamos uma Transacao e passamos a requisicao
        TransactionDto dto = transactionService.makeDeposit(depositRequest);
        TransactionResponse transactionResponse = new TransactionResponse(
                dto.getId(),
                dto.getType(),
                dto.getAmount(),
                dto.getSourceAccountId(),
                dto.getDestinationAccountId() != null ? dto.getDestinationAccountId() : null,
                null,
                dto.getTimestamp(),
                dto.getBalanceAfterOperation()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest){
        //Criamos uma Transacao e passamos a requisicao
        TransactionDto dto = transactionService.makeWithdrawal(withdrawRequest);
        TransactionResponse transactionResponse = new TransactionResponse(
                dto.getId(),
                dto.getType(),
                dto.getAmount(),
                dto.getSourceAccountId(),
                dto.getDestinationAccountId() != null ? dto.getDestinationAccountId() : null,
                null,
                dto.getTimestamp(),
                dto.getBalanceAfterOperation()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PostMapping("/internal-transfer")
    public ResponseEntity<TransactionResponse> internalTransfer(@Valid @RequestBody InternalTransferRequest internalTransferRequest){
        //Criamos uma Transacao e passamos a requisicao
        TransactionDto dto = transactionService.makeInternalTransfer(internalTransferRequest);
        TransactionResponse transactionResponse = new TransactionResponse(
                dto.getId(),
                dto.getType(),
                dto.getAmount(),
                dto.getSourceAccountId(),
                dto.getDestinationAccountId() != null ? dto.getDestinationAccountId() : null,
                null,
                dto.getTimestamp(),
                dto.getBalanceAfterOperation()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PostMapping("/external-transfer")
    public ResponseEntity<TransactionResponse> externalTransfer(@Valid @RequestBody ExternalTransferRequest externalTransferRequest){
        //Criamos uma Transacao e passamos a requisicao
        TransactionDto dto = transactionService.makeExternalTransfer(externalTransferRequest);
        TransactionResponse transactionResponse = new TransactionResponse(
                dto.getId(),
                dto.getType(),
                dto.getAmount(),
                dto.getSourceAccountId(),
                null,
                dto.getExternalDestinationAccountNumber(),
                dto.getTimestamp(),
                dto.getBalanceAfterOperation()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }
}
