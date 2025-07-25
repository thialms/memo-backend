package br.com.memo.controllers;

import br.com.memo.dto.requests.DeckRequest;
import br.com.memo.dto.responses.DeckResponse;
import br.com.memo.services.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @PostMapping("/user/{userId}/decks")
    public ResponseEntity<DeckResponse> createDeck(@PathVariable String userId, @RequestBody DeckRequest request){
        DeckResponse newDeck = deckService.createDeck( userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDeck);
    }

    @GetMapping("/user/{userId}/decks")
    public ResponseEntity<List<DeckResponse>> getDeckByUser(@PathVariable String userId){
        List<DeckResponse> decks = deckService.getDecksByUser(userId);
        return ResponseEntity.ok(decks);
    }

    @PutMapping("/user/{userId}/decks/{deckId}")
    public ResponseEntity<DeckResponse> updateDeck(@PathVariable String userId,
                                                   @PathVariable Long deckId,
                                                   @RequestBody DeckRequest request){
        DeckResponse updatedCard = deckService.updateDeck(userId, deckId, request);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/user/{userId}/decks/{deckId}")
    public ResponseEntity<Void> deleteDeck(@PathVariable String userId, @PathVariable Long deckId){
        deckService.deleteDeck(userId, deckId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{deckId}/progress")
    public DeckProgressResponse getDeckProgress(
            @PathVariable Long deckId,
            @AuthenticationPrincipal User user
    ) {
        return reviewService.getDeckProgress(deckId, user);
    }


}
