package com.eduardo.moneysaver.core.domain.repository;

import com.eduardo.moneysaver.core.domain.model.Movimento;
import com.eduardo.moneysaver.core.domain.model.StatusMovimento;
import com.eduardo.moneysaver.core.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
    List<Movimento> findAllByUser(User user);

    List<Movimento> findAllByStatus(StatusMovimento statusMovimento);
}
