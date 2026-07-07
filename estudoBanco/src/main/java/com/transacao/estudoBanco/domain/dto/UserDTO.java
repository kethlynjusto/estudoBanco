package com.transacao.estudoBanco.domain.dto;

import com.transacao.estudoBanco.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    String name;
    String lastName;
    String document;
    BigDecimal balance;
    String email;
    String password;
    UserType userType;
}
