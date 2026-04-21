package com.ighor.teste_tecnico_api_spring.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String cpf){
        super("Usuario nao foi encontrado com o cpf informado");
    }

    public UserNotFoundException(){
        super("Usuario nao foi encontrado");
    }
}
