package br.com.memo.mappers;

import br.com.memo.domain.card.Card;
import br.com.memo.dto.requests.CardRequest;
import br.com.memo.dto.responses.CardResponse;

public class CardMapper {

    public static Card toEntity(CardRequest request) {
        if (request == null) return null;

        Card card = new Card();
        card.setId(request.id());
        card.setFrontText(request.frontText());
        card.setBackText(request.backText());
        return card;
    }

    public static CardResponse toResponse(Card card) {
        if (card == null) return null;

        return new CardResponse(
                card.getId(),
                card.getFrontText(),
                card.getBackText()
        );
    }
}
