package br.com.memo.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para registro de um novo usuário")
public record RegisterRequest(
        @Schema(description = "Nome do usuário", example = "João Silva")
        String name,

        @Schema(description = "Email do usuário", example = "joao@email.com")
        String email,

        @Schema(description = "Senha do usuário", example = "senha123")
        String password
) {}
