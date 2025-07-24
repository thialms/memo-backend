package br.com.memo.repositories;

import br.com.memo.domain.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByDeckId(Long deckId);

    Optional<Card> findByIdAndDeckId(Long cardId, Long deckId);


}
