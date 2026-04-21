package com.ighor.teste_tecnico_api_spring.exception;

public class DataErrorException extends RuntimeException{

    public DataErrorException(String mensagem){
        super(mensagem);
    }
}
