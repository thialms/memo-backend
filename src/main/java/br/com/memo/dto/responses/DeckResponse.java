package br.com.memo.dto.responses;

import br.com.memo.domain.deck.Deck;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Representação de um baralho")
public record DeckResponse(
        @Schema(description = "ID do baralho", example = "2")
        Long id,

        @Schema(description = "Nome do baralho", example = "Algoritmos")
        String name,

        @Schema(description = "Lista de cartões que pertencem ao baralho")
        List<CardResponse> cards
) {
    public DeckResponse(Deck deck) {
        this(
                deck.getId(),
                deck.getName(),
                deck.getCards() != null
                        ? deck.getCards().stream().map(CardResponse::new).toList()
                        : List.of()
        );
    }
}
