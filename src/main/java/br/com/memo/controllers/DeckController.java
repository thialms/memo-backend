package br.com.memo.controllers;

import br.com.memo.domain.user.User;
import br.com.memo.dto.requests.DeckRequest;
import br.com.memo.dto.responses.DeckProgressResponse;
import br.com.memo.dto.responses.DeckResponse;
import br.com.memo.services.DeckService;
import br.com.memo.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Decks", description = "Gerenciamento de decks de estudo")
@RestController
@RequestMapping("/api/decks")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;
    private final ReviewService reviewService;

    @Operation(summary = "Criar um novo deck")
    @PostMapping
    public ResponseEntity<DeckResponse> createDeck(@AuthenticationPrincipal User user, @RequestBody DeckRequest request){
        DeckResponse newDeck = deckService.createDeck(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDeck);
    }

    @Operation(summary = "Listar decks do usuário")
    @GetMapping
    public ResponseEntity<List<DeckResponse>> getDeckByUser(@AuthenticationPrincipal User user){
        List<DeckResponse> decks = deckService.getDecksByUser(user.getId());
        return ResponseEntity.ok(decks);
    }

    @Operation(summary = "Atualizar um deck existente")
    @PutMapping("/{deckId}")
    public ResponseEntity<DeckResponse> updateDeck(@AuthenticationPrincipal User user,
                                                   @PathVariable Long deckId,
                                                   @RequestBody DeckRequest request){
        DeckResponse updatedCard = deckService.updateDeck(user.getId(), deckId, request);
        return ResponseEntity.ok(updatedCard);
    }

    @Operation(summary = "Excluir um deck")
    @DeleteMapping("/{deckId}")
    public ResponseEntity<Void> deleteDeck(@AuthenticationPrincipal User user, @PathVariable Long deckId){
        deckService.deleteDeck(user.getId(), deckId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obter progresso de revisão do deck")
    @GetMapping("/{deckId}/progress")
    public DeckProgressResponse getDeckProgress(@PathVariable Long deckId, @AuthenticationPrincipal User user) {
        return reviewService.getDeckProgress(deckId, user);
    }
}

