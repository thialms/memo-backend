package br.com.memo.controllers;

import br.com.memo.domain.card.Card;
import br.com.memo.dto.requests.CardRequest;
import br.com.memo.dto.responses.CardResponse;
import br.com.memo.repositories.CardRepository;
import br.com.memo.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks/card")
@RequiredArgsConstructor
public class CardController {

    private final CardRepository cardRepository;

    private final CardService cardService;

    @PostMapping("/decks/{deckId}/cards")
    public ResponseEntity<CardResponse> createCard(@PathVariable Long deckId, @RequestBody CardRequest cardRequest){
        CardResponse newCard = cardService.createCard(deckId, cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCard);
    }

    @GetMapping("/decks/{deckId}/cards")
    public ResponseEntity<List<CardResponse>> getCardsByDeck(@PathVariable Long deckId){
        List<CardResponse> cardResponseList = cardService.getCardsByDeck(deckId);
        return ResponseEntity.ok(cardResponseList);
    }

    @PostMapping("/decks/{deckId}/cards/{cardId}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable Long deckid,
                                                   @PathVariable Long cardId,
                                                   @RequestBody CardRequest request){
        CardResponse updatedCard = cardService.updateCard(deckid, cardId, request);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/decks/{deckId}/cards/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long deckId, @PathVariable Long cardId){
         cardService.deleteCard(deckId, cardId);
         return ResponseEntity.noContent().build();
    }
}
