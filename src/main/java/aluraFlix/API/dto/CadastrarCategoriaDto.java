package aluraFlix.API.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastrarCategoriaDto(
        Long id_categoria,
        @NotBlank
        String titulo,
        @NotBlank
        String cor,
        Boolean ativo) {
}
