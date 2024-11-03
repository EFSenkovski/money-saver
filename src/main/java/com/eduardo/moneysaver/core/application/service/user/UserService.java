package com.eduardo.moneysaver.core.application.service.user;

import com.eduardo.moneysaver.core.domain.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<UserDetails> findUserDetailsByEmail(String email);
    void saveUser(User user);
}
