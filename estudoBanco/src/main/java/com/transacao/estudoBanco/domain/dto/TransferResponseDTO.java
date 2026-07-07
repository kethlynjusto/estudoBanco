package com.transacao.estudoBanco.domain.dto;

public record TransferResponseDTO(String status, TransferResponseDTO.Data data) {
    public record Data(Boolean authorization){}
}

