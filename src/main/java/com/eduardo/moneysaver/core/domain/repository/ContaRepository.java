package com.eduardo.moneysaver.core.domain.repository;

import com.eduardo.moneysaver.core.domain.model.Conta;
import com.eduardo.moneysaver.core.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findAllByUser(User user);
    Optional<Conta> findByIdAndUser(Long id, User user);
}
