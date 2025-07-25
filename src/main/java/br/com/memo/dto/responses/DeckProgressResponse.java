package br.com.memo.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Progresso de revisão do baralho")
public record DeckProgressResponse(
        @Schema(description = "Total de cartões", example = "20")
        int total,

        @Schema(description = "Cartões revisados hoje", example = "5")
        int reviewedToday,

        @Schema(description = "Cartões agendados para hoje", example = "8")
        int dueToday,

        @Schema(description = "Cartões atrasados", example = "3")
        int overdue
) {}
