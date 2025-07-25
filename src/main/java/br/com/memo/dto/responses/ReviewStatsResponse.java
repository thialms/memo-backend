package br.com.memo.dto.responses;

import java.time.LocalDate;

public record ReviewStatsResponse(
        int quality,
        int repetition,
        int interval,
        double easiness,
        LocalDate nextReview
) {}
