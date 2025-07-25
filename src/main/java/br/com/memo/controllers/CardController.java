package br.com.memo.controllers;

import br.com.memo.domain.card.Card;
import br.com.memo.dto.requests.CardRequest;
import br.com.memo.dto.responses.CardResponse;
import br.com.memo.repositories.CardRepository;
import br.com.memo.services.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cartões", description = "Gerenciamento de cartões (cards) dentro de decks")
@RestController
@RequestMapping("/api/decks/{deckId}/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardRepository cardRepository;
    private final CardService cardService;

    @Operation(summary = "Criar um novo cartão no deck")
    @PostMapping
    public ResponseEntity<CardResponse> createCard(@PathVariable Long deckId, @RequestBody CardRequest cardRequest){
        CardResponse newCard = cardService.createCard(deckId, cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCard);
    }

    @Operation(summary = "Listar cartões de um deck")
    @GetMapping
    public ResponseEntity<List<CardResponse>> getCardsByDeck(@PathVariable Long deckId){
        List<CardResponse> cardResponseList = cardService.getCardsByDeck(deckId);
        return ResponseEntity.ok(cardResponseList);
    }

    @Operation(summary = "Atualizar cartão do deck")
    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable Long deckId,
                                                   @PathVariable Long cardId,
                                                   @RequestBody CardRequest request){
        CardResponse updatedCard = cardService.updateCard(deckId, cardId, request);
        return ResponseEntity.ok(updatedCard);
    }

    @Operation(summary = "Excluir cartão do deck")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long deckId, @PathVariable Long cardId){
        cardService.deleteCard(deckId, cardId);
        return ResponseEntity.noContent().build();
    }
}

