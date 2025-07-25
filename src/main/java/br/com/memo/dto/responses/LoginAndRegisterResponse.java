package br.com.memo.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o nome do usuário e o token JWT")
public record LoginAndRegisterResponse(
        @Schema(description = "Nome do usuário", example = "Maria")
        String name,

        @Schema(description = "Token JWT gerado após login ou registro")
        String token
) {}
