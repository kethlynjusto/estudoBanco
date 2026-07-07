package com.transacao.estudoBanco.domain.repository;

import com.transacao.estudoBanco.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    boolean existsByDocument(String document);
}
