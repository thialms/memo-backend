package br.com.memo.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de criação ou edição de um cartão")
public record CardRequest(
        @Schema(description = "ID do cartão (opcional para criação)", example = "1")
        Long id,

        @Schema(description = "Texto da frente do cartão", example = "O que é POO?")
        String frontText,

        @Schema(description = "Texto do verso do cartão", example = "Programação Orientada a Objetos")
        String backText
) {}
