package com.transacao.estudoBanco.domain.service;

import com.transacao.estudoBanco.domain.dto.UserDTO;
import com.transacao.estudoBanco.domain.exception.InsufficientBalanceException;
import com.transacao.estudoBanco.domain.exception.UnauthorizedTransactionException;
import com.transacao.estudoBanco.domain.user.User;
import com.transacao.estudoBanco.domain.user.UserType;
import com.transacao.estudoBanco.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void validateTransaction (User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new InsufficientBalanceException("Usuário Lojista não está autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new InsufficientBalanceException("Saldo insuficiente");
        }

    }

    public User findUserById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new UnauthorizedTransactionException("Usuário não encontrado"));
    }

    public void saveUser(User user){
        repository.save(user);
    }

    public User createUser(UserDTO user){
        if(repository.existsByDocument(user.getDocument())){
            throw new IllegalArgumentException("Já existe um cadastro para esse CPF.");
        }

        User newUser = User.builder()
                .firstName(user.getName())
                .lastName(user.getLastName())
                .document(user.getDocument())
                .email(user.getEmail())
                .password(user.getPassword())
                .userType(user.getUserType())
                .build();
        repository.save(newUser);

        return newUser;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }
}
