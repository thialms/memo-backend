package br.com.memo.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Estatísticas da revisão de um cartão segundo o algoritmo SM-2")
public record ReviewStatsResponse(
        @Schema(description = "Qualidade da resposta", example = "4")
        int quality,

        @Schema(description = "Número de repetições", example = "2")
        int repetition,

        @Schema(description = "Intervalo até a próxima revisão (dias)", example = "3")
        int interval,

        @Schema(description = "Fator de facilidade", example = "2.5")
        double easiness,

        @Schema(description = "Data da próxima revisão", example = "2025-07-26")
        LocalDate nextReview
) {}
