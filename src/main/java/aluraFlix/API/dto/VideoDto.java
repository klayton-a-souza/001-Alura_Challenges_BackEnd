package aluraFlix.API.dto;

import aluraFlix.API.model.Video;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record VideoDto(
        @NotNull
        Long id_video,
        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        @NotBlank
        @URL
        String url,
        @NotNull
        Boolean ativo,
        Long id_categoria) {

        public VideoDto(Video video){
                this(video.getId_video(), video.getTitulo(), video.getDescricao(), video.getUrl(), video.getAtivo(), video.getCategoria().getId_categoria());
        }
}
