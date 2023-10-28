package aluraFlix.API.dto.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDto(
        @NotBlank
        String login,
        @NotBlank
        String senha) {
}
