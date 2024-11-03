package com.eduardo.moneysaver.core.domain.repository;

import com.eduardo.moneysaver.core.domain.model.Tag;
import com.eduardo.moneysaver.core.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByUser(User user);

    Optional<Tag> findByIdAndUser(Long id, User user);
}
