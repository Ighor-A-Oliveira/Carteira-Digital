package com.ighor.teste_tecnico_api_spring.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(Long accountNumber){
        super("Conta nao foi encontrado com o numero de conta:"+ accountNumber);
    }

    public AccountNotFoundException(){
        super("Conta nao foi encontrada");
    }
}
