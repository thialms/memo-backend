package br.com.memo.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados enviados ao revisar um cartão")
public record ReviewRequest(
        @Schema(description = "ID do cartão", example = "5")
        Long cardId,

        @Schema(description = "Qualidade da resposta do usuário (0 a 5)", example = "4")
        int quality
) {}
