package com.eduardo.moneysaver.core.application.controller.auth;

import com.eduardo.moneysaver.core.application.controller.auth.dto.AuthDto;
import com.eduardo.moneysaver.core.application.controller.auth.dto.LoginResp;
import com.eduardo.moneysaver.core.application.controller.auth.dto.RegisterUserDto;
import com.eduardo.moneysaver.core.application.service.user.UserService;
import com.eduardo.moneysaver.core.domain.model.User;
import com.eduardo.moneysaver.core.infra.security.token.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // ProviderManager na verdade vai ser a implementação de AuthenticationManager injetada neste objeto, de acordo com a configuração da class SecurityConfig
    private final AuthenticationManager providerManager;
    private final UserService userService;

    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager providerManager, UserService userService, TokenService tokenService) {
        this.providerManager = providerManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDto authDto) {
        var unauthenticatedAuthentication = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password()); //UsernamePasswordAuthenticationToken é uma implementação de Authentication fornecida pelo Spring. Nesta linha ele é criado com as credenciais, mas ainda não está autenticado
        var authenticatedAuthentication = this.providerManager.authenticate(unauthenticatedAuthentication); //Provider pega o Authentication e tenta autenticar
        var token = tokenService.generateToken((User) authenticatedAuthentication.getPrincipal());

        return ResponseEntity.ok(new LoginResp(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        if (this.userService.findUserDetailsByEmail(registerUserDto.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        var encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDto.password());
        var newUser = new User(registerUserDto.email(), encryptedPassword, registerUserDto.role()); // isto tornará necessário criar um Constructor na classe User com estes parâmetros

        this.userService.saveUser(newUser);

        return ResponseEntity.ok().build();
    }
}
