package br.com.memo.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações básicas do usuário autenticado")
public record UserResponse(
        @Schema(description = "Nome do usuário", example = "Thiago Almeida")
        String name
) {}
