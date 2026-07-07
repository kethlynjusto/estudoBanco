package com.transacao.estudoBanco.domain.dto;

public record NotificationResponseDTO(String status, NotificationResponseDTO.Data data) {
    public record Data(String message) {}
}