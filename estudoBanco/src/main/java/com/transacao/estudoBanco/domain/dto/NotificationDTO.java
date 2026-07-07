package com.transacao.estudoBanco.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationDTO {

    String email;
    String message;
}
