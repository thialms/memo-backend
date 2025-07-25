package br.com.memo.repositories;

import br.com.memo.domain.card.Card;
import br.com.memo.domain.review.CardReviewData;
import br.com.memo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CardReviewDataRepository extends JpaRepository<CardReviewData, Long> {

    Optional<CardReviewData> findByUserAndCard(User user, Card card);

    List<CardReviewData> findByUserAndNextReviewLessThanEqual(User user, LocalDate nextReview);

    List<CardReviewData> findByUserAndCardDeckId(User user, Long deckId);

}
