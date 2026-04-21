package com.ighor.teste_tecnico_api_spring.service;

import com.ighor.teste_tecnico_api_spring.config.TokenConfig;
import com.ighor.teste_tecnico_api_spring.dto.entity.UserDto;
import com.ighor.teste_tecnico_api_spring.dto.request.LoginRequest;
import com.ighor.teste_tecnico_api_spring.dto.request.RegisterUserRequest;
import com.ighor.teste_tecnico_api_spring.dto.response.LoginResponse;
import com.ighor.teste_tecnico_api_spring.entity.Role;
import com.ighor.teste_tecnico_api_spring.entity.User;
import com.ighor.teste_tecnico_api_spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, TokenConfig tokenConfig){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }


    @Transactional
    public UserDto createUser(RegisterUserRequest registerUserRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "ANÔNIMO";

        //Cria um novo usuário
        User newUser = new User();
        //Copia os dados da requisição para o usuário
        //passwordEncoder.encode() esta criptografando a senha
        newUser.setName(registerUserRequest.name());
        newUser.setEmail(registerUserRequest.email());
        newUser.setCpf(registerUserRequest.cpf());
        newUser.setPassword(passwordEncoder.encode(registerUserRequest.password()));
        newUser.setRoles(Set.of(Role.USER));

        logger.info("Usuário: {}, Endpoint: {}, Data: {}, Payload: {}",
                username,
                "/user/register",
                LocalDateTime.now(),
                registerUserRequest
        );

        userRepository.save(newUser);

        UserDto dto = new UserDto();
        dto.setName(newUser.getName());
        dto.setEmail(newUser.getEmail());
        dto.setCpf(newUser.getCpf());

        return dto;
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        //Cria o token de autenticação interno do Spring
        //Spring Security só aceita login no formato UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        //Autentica no Spring Security:
        //Busca o usuário no banco (UserDetailsService)
        //Compara a senha com BCrypt (PasswordEncoder)
        //se estiver certo: retorna um objeto Authentication
        //se estiver errado: lança BadCredentialsException
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        //Recupera o usuário autenticado do banco de dados
        User user = (User) authentication.getPrincipal();
        //Gera o JWT para o usuário logado
        String token = tokenConfig.generateToken(user);

        logger.info("Usuário: {}, Endpoint: /auth/login, Data: {}, Payload: {}",
                loginRequest.email(),
                LocalDateTime.now(),
                loginRequest);

        //Retorna o token no body
        return new LoginResponse(token);
    }
}

