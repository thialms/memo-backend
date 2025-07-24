package br.com.memo.repositories;

import br.com.memo.domain.deck.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findByUserId(String userId);

    Optional<Deck> findByIdAndUserId(Long deckId, String userId);


}
