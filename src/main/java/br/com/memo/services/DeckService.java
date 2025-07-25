package br.com.memo.services;

import br.com.memo.domain.card.Card;
import br.com.memo.domain.deck.Deck;
import br.com.memo.domain.user.User;
import br.com.memo.dto.requests.CardRequest;
import br.com.memo.dto.requests.DeckRequest;
import br.com.memo.dto.responses.CardResponse;
import br.com.memo.dto.responses.DeckResponse;
import br.com.memo.repositories.DeckRepository;
import br.com.memo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

    public final DeckRepository deckRepository;

    public final UserRepository userRepository;

    public final ReviewService reviewService;

    public DeckResponse createDeck(String userId, DeckRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Deck deck = new Deck();
        deck.setName(request.name());
        deck.setUser(user);

        if (request.cards() != null && !request.cards().isEmpty()) {
            List<Card> cards = request.cards().stream().map(cardReq -> {
                Card card = new Card();
                card.setFrontText(cardReq.frontText());
                card.setBackText(cardReq.backText());
                card.setDeck(deck);
                return card;
            }).toList();
            deck.setCards(cards);
        }

        deckRepository.save(deck);

        if (deck.getCards() != null && !deck.getCards().isEmpty()) {
            reviewService.createReviewDataForCards(user, deck.getCards());
        }

        List<CardResponse> cardResponses = deck.getCards() != null
                ? deck.getCards().stream()
                .map(card -> new CardResponse(card.getId(), card.getFrontText(), card.getBackText()))
                .toList()
                : List.of();

        return new DeckResponse(deck.getId(), deck.getName(), cardResponses);
    }

    public List<DeckResponse> getDecksByUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Deck> decks = deckRepository.findByUserId(userId);

        return decks.stream()
                .map(DeckResponse::new)
                .toList();
    }

    public DeckResponse updateDeck(String userId, Long deckId, DeckRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new EntityNotFoundException("Deck not found"));

        if (!deck.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Este deck não pertence ao user informado.");
        }

        deck.setName(request.name());

        deckRepository.save(deck);

        return new DeckResponse(deck);
    }

    public void deleteDeck(String userId, Long deckId) {
        Deck deck = deckRepository.findByIdAndUserId(deckId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Deck not found for this user"));

        deckRepository.delete(deck);
    }

}
