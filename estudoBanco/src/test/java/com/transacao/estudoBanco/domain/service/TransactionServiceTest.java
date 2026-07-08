package com.transacao.estudoBanco.domain.service;

import com.transacao.estudoBanco.domain.dto.TransactionDTO;
import com.transacao.estudoBanco.domain.dto.TransferResponseDTO;
import com.transacao.estudoBanco.domain.exception.UnauthorizedTransactionException;
import com.transacao.estudoBanco.domain.repository.TransactionRepository;
import com.transacao.estudoBanco.domain.transaction.Transaction;
import com.transacao.estudoBanco.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    UserService userService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    TransactionRepository repository;

    @Mock
    NotificationService notificationService;

    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(userService, repository, notificationService);
        ReflectionTestUtils.setField(transactionService, "restTemplate", restTemplate);
    }

    @Test
    @DisplayName("Should create transaction successfully when authorized")
    void createTransaction_whenAuthorized_shouldSucceed() throws Exception {
        User sender = User.builder().id(1L).balance(new BigDecimal("100")).build();
        User receiver = User.builder().id(1L).balance(new BigDecimal("100")).build();
        TransactionDTO dto = new TransactionDTO(new BigDecimal(50), 1L, 2L);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(restTemplate.getForEntity(anyString(), eq(TransferResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(
                        new TransferResponseDTO("SUCCESS", new TransferResponseDTO.Data(true)),
                        HttpStatus.OK));

        TransactionDTO result = transactionService.createTransaction(dto);

        assertThat(result).isEqualTo(dto);

        assertThat(sender.getBalance()).isEqualByComparingTo("50");
        assertThat(receiver.getBalance()).isEqualByComparingTo("150");

        verify(repository).save(any(Transaction.class));
        verify(userService, times(2)).saveUser(any(User.class));
    }

    @Test
    @DisplayName("Should throw UnauthorizedTransactionException when not authorized")
    void createTransaction_whenNotAuthorized_shouldThrow() throws Exception {
        User sender = User.builder().id(1L).balance(new BigDecimal("100")).build();
        User receiver = User.builder().id(2L).balance(BigDecimal.ZERO).build();
        TransactionDTO dto = new TransactionDTO(new BigDecimal(50), 1L, 2L);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        // simula que a API externa NEGOU a autorização
        when(restTemplate.getForEntity(anyString(), eq(TransferResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(
                        new TransferResponseDTO("FAIL", new TransferResponseDTO.Data(false)), HttpStatus.OK));

        assertThatThrownBy(() -> transactionService.createTransaction(dto))
                .isInstanceOf(UnauthorizedTransactionException.class);

        verify(repository, never()).save(any());
    }

}