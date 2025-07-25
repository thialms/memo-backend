package br.com.memo.services;

import br.com.memo.domain.card.Card;
import br.com.memo.domain.review.CardReviewData;
import br.com.memo.domain.review.sm2.SM2Algorithm;
import br.com.memo.domain.user.User;
import br.com.memo.dto.responses.DeckProgressResponse;
import br.com.memo.dto.responses.ReviewStatsResponse;
import br.com.memo.repositories.CardReviewDataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final CardReviewDataRepository cardReviewDataRepository;

    public void createReviewDataForCards(User user, List<Card> cards) {
        List<CardReviewData> cardReviewDataList = cards.stream()
                .filter(card -> cardReviewDataRepository.findByUserAndCard(user, card).isEmpty())
                .map(card -> new CardReviewData(
                        null,
                        user,
                        card,
                        0,
                        2.5,
                        0,
                        LocalDate.now(),
                        LocalDate.now()
                )).toList();

        cardReviewDataRepository.saveAll(cardReviewDataList);
    }


    public ReviewStatsResponse reviewCard(User user, Card card, int quality) {
        CardReviewData reviewData = cardReviewDataRepository
                .findByUserAndCard(user, card)
                .orElseThrow(() -> new EntityNotFoundException("Dados de revisão não encontrados"));

        SM2Algorithm.apply(reviewData, quality);

        cardReviewDataRepository.save(reviewData);

        return new ReviewStatsResponse(
                quality,
                reviewData.getRepetition(),
                reviewData.getInterval(),
                reviewData.getEasiness(),
                reviewData.getNextReview()
        );
    }

    public List<Card> getCardsToReviewToday(User user) {
        LocalDate today = LocalDate.now();
        return cardReviewDataRepository
                .findByUserAndNextReviewLessThanEqualOrderByNextReviewAsc(user, today)
                .stream()
                .map(CardReviewData::getCard)
                .toList();
    }

    public DeckProgressResponse getDeckProgress(Long deckId, User user) {
        List<CardReviewData> reviews = cardReviewDataRepository.findByUserAndCardDeckId(user, deckId);
        LocalDate today = LocalDate.now();

        int total = reviews.size();
        int reviewedToday = (int) reviews.stream()
                .filter(r -> r.getLastReview() != null && r.getLastReview().isEqual(today))
                .count();
        int dueToday = (int) reviews.stream()
                .filter(r -> r.getNextReview() != null && r.getNextReview().isEqual(today))
                .count();
        int overdue = (int) reviews.stream()
                .filter(r -> r.getNextReview() != null && r.getNextReview().isBefore(today))
                .count();

        return new DeckProgressResponse(total, reviewedToday, dueToday, overdue);
    }

}

