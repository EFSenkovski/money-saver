package com.eduardo.moneysaver.core.infra.security.config;

import com.eduardo.moneysaver.core.infra.security.filter.JwtTokenSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    JwtTokenSecurityFilter jwtTokenSecurityFilter;

    @Autowired
    public SecurityConfig(JwtTokenSecurityFilter jwtTokenSecurityFilter) {
        this.jwtTokenSecurityFilter = jwtTokenSecurityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //csrfObject -> csrfObject.disable() // desabilitar cross-site request forgery. Pode ser desabilitado ou não, dependendo do projeto
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // define que a autenticação será stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // permite que os users loguem no sistema
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() // não indicado, já que qualquer um pode criar um user como admin dessa forma
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Utiliza o AuthenticationManagerBuilder para entregar uma implementação do AuthenticationManager
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //O Spring Security irá utilizar esse password encoder
    }
}
