package br.com.memo.domain.review.sm2;

import br.com.memo.domain.review.CardReviewData;

import java.time.LocalDate;

public class SM2Algorithm {

    public static void apply(CardReviewData cardReviewData, int quality) {
        if (quality >= 3) {
            // Atualiza intervalo baseado na repetição
            if (cardReviewData.getRepetition() == 0) {
                cardReviewData.setInterval(1);
            } else if (cardReviewData.getRepetition() == 1) {
                cardReviewData.setInterval(6);
            } else {
                int newInterval = (int) Math.round(cardReviewData.getInterval() * cardReviewData.getEasiness());
                cardReviewData.setInterval(newInterval);
            }

            // Atualiza o fator de facilidade (easiness)
            double newEasiness = cardReviewData.getEasiness() +
                    (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
            cardReviewData.setEasiness(Math.max(1.3, newEasiness));

            // Incrementa repetição
            cardReviewData.setRepetition(cardReviewData.getRepetition() + 1);
        } else {
            // Resposta ruim: reinicia
            cardReviewData.setRepetition(0);
            cardReviewData.setInterval(1);
        }

        // Define próxima revisão
        cardReviewData.setNextReview(LocalDate.now().plusDays(cardReviewData.getInterval()));

    }
}
