package br.com.memo.dto.responses;

import br.com.memo.domain.card.Card;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação de um cartão")
public record CardResponse(
        @Schema(description = "ID do cartão", example = "10")
        Long id,

        @Schema(description = "Texto da frente", example = "O que significa API?")
        String frontText,

        @Schema(description = "Texto do verso", example = "Application Programming Interface")
        String backText
) {
    public CardResponse(Card card) {
        this(card.getId(), card.getFrontText(), card.getBackText());
    }
}
