package com.transacao.estudoBanco.domain.dto;

public record ErrorResponseDTO(int status, String error, String message, String path) {}
