package br.com.memo.dto.requests;

import java.util.List;

public record DeckRequest(Long id, String name, List<CardRequest> cards) {
}
