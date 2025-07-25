package br.com.memo.dto.responses;

public record DeckProgressResponse(
        int total,
        int reviewedToday,
        int dueToday,
        int overdue
) {}
