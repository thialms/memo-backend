package br.com.memo.services;

import br.com.memo.domain.card.Card;
import br.com.memo.domain.deck.Deck;
import br.com.memo.dto.requests.CardRequest;
import br.com.memo.dto.responses.CardResponse;
import br.com.memo.repositories.CardRepository;
import br.com.memo.repositories.DeckRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    private final DeckRepository deckRepository;

    public CardResponse createCard(Long deckId, CardRequest request) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new EntityNotFoundException("Deck not found"));

        Card card = new Card();
        card.setFrontText(request.frontText());
        card.setBackText(request.backText());
        card.setDeck(deck);

        cardRepository.save(card);

        return new CardResponse(card.getId(), card.getFrontText(), card.getBackText());
    }


    public List<CardResponse> getCardsByDeck(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new EntityNotFoundException("Deck not found"));

        List<Card> cards = cardRepository.findByDeckId(deckId);

        return cards.stream()
                .map(CardResponse::new)
                .toList();
    }


    public CardResponse updateCard(Long deckId, Long cardId, CardRequest request) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new EntityNotFoundException("Deck not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));

        if (!card.getDeck().getId().equals(deck.getId())) {
            throw new IllegalArgumentException("Este card não pertence ao deck informado.");
        }

        card.setFrontText(request.frontText());
        card.setBackText(request.backText());

        cardRepository.save(card);

        return new CardResponse(card.getId(), card.getFrontText(), card.getBackText());
    }

    public void deleteCard(Long deckId, Long cardId) {
        Card card = cardRepository.findByIdAndDeckId(cardId, deckId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found in specified deck"));

        cardRepository.delete(card);
    }

}
