package com.ighor.teste_tecnico_api_spring.dto.entity;

import com.ighor.teste_tecnico_api_spring.entity.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String cpf;

    public UserDto(String name, String email, String cpf){
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }
}
