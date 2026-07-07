package com.transacao.estudoBanco.domain.controller;

import com.transacao.estudoBanco.domain.dto.UserDTO;
import com.transacao.estudoBanco.domain.service.UserService;
import com.transacao.estudoBanco.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDTO dto){
        User newUser = userService.createUser(dto);

        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }


}
