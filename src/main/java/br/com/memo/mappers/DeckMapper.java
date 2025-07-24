package br.com.memo.mappers;

import br.com.memo.domain.card.Card;
import br.com.memo.domain.deck.Deck;
import br.com.memo.dto.requests.DeckRequest;
import br.com.memo.dto.responses.CardResponse;
import br.com.memo.dto.responses.DeckResponse;

import java.util.List;
import java.util.stream.Collectors;

public class DeckMapper {

    public static Deck toEntity(DeckRequest request) {
        if (request == null) return null;

        Deck deck = new Deck();
        deck.setId(request.id());
        deck.setName(request.name());

        if (request.cards() != null) {
            List<Card> cards = request.cards().stream().map(cardReq -> {
                Card card = new Card();
                card.setId(cardReq.id());
                card.setFrontText(cardReq.frontText());
                card.setBackText(cardReq.backText());
                card.setDeck(deck);
                return card;
            }).collect(Collectors.toList());
            deck.setCards(cards);
        }

        return deck;
    }

    public static DeckResponse toResponse(Deck deck) {
        if (deck == null) return null;

        List<CardResponse> cardResponses = deck.getCards().stream()
                .map(card -> new CardResponse(card.getId(), card.getFrontText(), card.getBackText()))
                .toList();

        return new DeckResponse(deck.getId(), deck.getName(), cardResponses);
    }

}
