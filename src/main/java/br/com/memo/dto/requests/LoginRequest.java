package br.com.memo.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciais para login")
public record LoginRequest(
        @Schema(description = "Email do usuário", example = "exemplo@email.com")
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        String password
) {}
