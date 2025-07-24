package br.com.memo.dto.responses;

import br.com.memo.domain.deck.Deck;

import java.util.List;

public record DeckResponse(Long id, String name, List<CardResponse> cards) {

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
