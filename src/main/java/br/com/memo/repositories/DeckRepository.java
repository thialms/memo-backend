package br.com.memo.repositories;

import br.com.memo.domain.deck.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}
