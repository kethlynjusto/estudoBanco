package com.transacao.estudoBanco.domain.repositories;

import com.transacao.estudoBanco.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDocument(String document);
    Optional<User> findById(Long id);
}
