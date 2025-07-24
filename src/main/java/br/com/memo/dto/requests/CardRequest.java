package br.com.memo.dto.requests;

import lombok.Builder;

@Builder
public record CardRequest(Long id, String frontText, String backText) {
}
