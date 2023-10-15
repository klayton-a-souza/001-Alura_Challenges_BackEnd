package aluraFlix.API.dto;

import aluraFlix.API.model.Video;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CadastroVideoDto(
        Long idVideo,
        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        @NotBlank
        @URL
        String url,
        Boolean ativo) {
    public CadastroVideoDto(Video video) {
        this(video.getId_video(), video.getTitulo(), video.getDescricao(), video.getUrl(), video.getAtivo());
    }
}
