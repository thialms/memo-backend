package br.com.memo.dto.responses;

import br.com.memo.domain.card.Card;

public record CardResponse(Long id, String frontText, String backText) {

    public CardResponse(Card card) {
        this(card.getId(), card.getFrontText(), card.getBackText());
    }
}
