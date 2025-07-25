package br.com.memo.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dados de criação ou edição de um baralho")
public record DeckRequest(
        @Schema(description = "ID do baralho (opcional para criação)", example = "1")
        Long id,

        @Schema(description = "Nome do baralho", example = "Java Básico")
        String name,

        @Schema(description = "Lista de cartões que pertencem ao baralho")
        List<CardRequest> cards
) {}
