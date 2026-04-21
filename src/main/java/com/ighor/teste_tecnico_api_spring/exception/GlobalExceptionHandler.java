package com.ighor.teste_tecnico_api_spring.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handleAccountNotFoundException(AccountNotFoundException exception){
        return ErrorResponse.of(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handleUserNotFoundException(UserNotFoundException exception){
        return ErrorResponse.of(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DataErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataErrorException(DataErrorException exception) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    // Erros de validação em @RequestBody (ex: @Valid em DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        //Preparando uma lista com os detalhes do erro
        List<ErrorResponse.FieldError> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                //cada erro fara parte de uma lista do objeto FieldError dentro de ErrorResponse
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        //retornando a exception tratada
        return ErrorResponse.ofValidation(HttpStatus.UNPROCESSABLE_ENTITY, fieldErrors);
    }

    // Erros de validação em @RequestParam ou @PathVariable
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {

        List<ErrorResponse.FieldError> fieldErrors = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponse.FieldError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .toList();
        return ErrorResponse.ofValidation(HttpStatus.BAD_REQUEST, fieldErrors);
    }



    // Erros genéricos não mapeados
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception exception) {
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }
}
