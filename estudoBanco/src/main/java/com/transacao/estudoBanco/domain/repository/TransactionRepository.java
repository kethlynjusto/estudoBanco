package com.transacao.estudoBanco.domain.repository;

import com.transacao.estudoBanco.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
