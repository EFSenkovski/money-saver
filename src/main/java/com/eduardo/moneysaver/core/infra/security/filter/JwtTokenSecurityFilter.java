package com.eduardo.moneysaver.core.infra.security.filter;

import com.eduardo.moneysaver.core.domain.repository.UserRepository;
import com.eduardo.moneysaver.core.infra.security.token.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenSecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;



    @Autowired
    public JwtTokenSecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", ""); // o Header sempre vem com Bearer <...token...>
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.getTokenFromRequest(request);
        if (token != null) {
            var subject = tokenService.validateTokenAndReturnSubject(token); // metodo que criamos para validar o token e retornar o subject
            UserDetails userDetails = userRepository.findByEmail(subject); // recupera dados baseado no login que veio no token
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // cria objeto authentication baseado no userDetails
            SecurityContextHolder.getContext().setAuthentication(authentication); // seta a authentication no contexto da requisição
        }
        filterChain.doFilter(request,response);
    }
}
