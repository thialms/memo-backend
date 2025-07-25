package br.com.memo.domain.review;

import br.com.memo.domain.card.Card;
import br.com.memo.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;;

@Entity
@Table(
        name = "card_review_data",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "card_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardReviewData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    private int repetition;
    private double easiness;
    private int interval;
    private LocalDate nextReview;
    private LocalDate lastReview;
}

