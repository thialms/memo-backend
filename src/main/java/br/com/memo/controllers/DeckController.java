package br.com.memo.controllers;

import br.com.memo.domain.user.User;
import br.com.memo.dto.requests.DeckRequest;
import br.com.memo.dto.responses.DeckProgressResponse;
import br.com.memo.dto.responses.DeckResponse;
import br.com.memo.services.DeckService;
import br.com.memo.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decks")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<DeckResponse> createDeck(@AuthenticationPrincipal User user, @RequestBody DeckRequest request){
        DeckResponse newDeck = deckService.createDeck(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDeck);
    }

    @GetMapping
    public ResponseEntity<List<DeckResponse>> getDeckByUser(@AuthenticationPrincipal User user){
        List<DeckResponse> decks = deckService.getDecksByUser(user.getId());
        return ResponseEntity.ok(decks);
    }

    @PutMapping("/{deckId}")
    public ResponseEntity<DeckResponse> updateDeck(@AuthenticationPrincipal User user,
                                                   @PathVariable Long deckId,
                                                   @RequestBody DeckRequest request){
        DeckResponse updatedCard = deckService.updateDeck(user.getId(), deckId, request);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<Void> deleteDeck(@AuthenticationPrincipal User user, @PathVariable Long deckId){
        deckService.deleteDeck(user.getId(), deckId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{deckId}/progress")
    public DeckProgressResponse getDeckProgress(@PathVariable Long deckId, @AuthenticationPrincipal User user) {
        return reviewService.getDeckProgress(deckId, user);
    }
}

