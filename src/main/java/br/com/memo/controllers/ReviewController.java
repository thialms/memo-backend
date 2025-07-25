package br.com.memo.controllers;

import br.com.memo.domain.card.Card;
import br.com.memo.domain.user.User;
import br.com.memo.dto.requests.ReviewRequest;
import br.com.memo.dto.responses.ReviewStatsResponse;
import br.com.memo.repositories.CardRepository;
import br.com.memo.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Revisões", description = "Sistema de revisão de cartões com algoritmo de repetição espaçada")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final CardRepository cardRepository;

    @Operation(summary = "Listar cartões para revisar hoje")
    @GetMapping("/today")
    public List<Card> getCardsToReviewToday(@AuthenticationPrincipal User user) {
        return reviewService.getCardsToReviewToday(user);
    }

    @Operation(summary = "Avaliar cartão com base na qualidade da resposta")
    @PostMapping
    public ResponseEntity<ReviewStatsResponse> reviewCard(
            @RequestBody ReviewRequest request,
            @AuthenticationPrincipal User user
    ) {
        Card card = cardRepository.findById(request.cardId())
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        ReviewStatsResponse stats = reviewService.reviewCard(user, card, request.quality());
        return ResponseEntity.ok(stats);
    }
}


