package com.transacao.estudoBanco.domain.controller;

import com.transacao.estudoBanco.domain.dto.TransactionDTO;
import com.transacao.estudoBanco.domain.service.TransactionService;
import com.transacao.estudoBanco.domain.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transacao")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        TransactionDTO newTransaction = transactionService.createTransaction(transactionDTO);

        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
