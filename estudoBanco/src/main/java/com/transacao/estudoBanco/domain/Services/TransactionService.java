package com.transacao.estudoBanco.domain.Services;

import com.transacao.estudoBanco.domain.dto.TransactionDTO;
import com.transacao.estudoBanco.domain.dto.TransferResponseDTO;
import com.transacao.estudoBanco.domain.transaction.Transaction;
import com.transacao.estudoBanco.domain.user.User;
import com.transacao.estudoBanco.domain.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository repository;
    private final NotificationService notificationService;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public void createTransaction(TransactionDTO transactionDTO) throws Exception {
        BigDecimal value = transactionDTO.getValue();
        User sender = userService.findUserById(transactionDTO.getPayer());
        User receiver = userService.findUserById(transactionDTO.getPayee());

        userService.validateTransaction(sender, value);

        if(!authorizeTransaction(sender, value)){
            throw new Exception("Não autorizado");
        }

        Transaction newTransaction = Transaction.builder()
                .amount(value)
                .sender(sender)
                .receiver(receiver)
                .timeStamp(LocalDateTime.now())
                .build();

        repository.save(newTransaction);

        sender.setBalance(sender.getBalance().subtract(value));
        receiver.setBalance(receiver.getBalance().add(value));

        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender, "Seu dinheiro foi enviado");
        notificationService.sendNotification(receiver, "Você recebeu um dinheiro");
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<TransferResponseDTO> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", TransferResponseDTO.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            TransferResponseDTO body = authorizationResponse.getBody();
            return body != null && body.data().authorization();
        }
        return false;
    }
}
