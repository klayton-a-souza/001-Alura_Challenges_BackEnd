package aluraFlix.API.dto;

import aluraFlix.API.model.Video;

public record AtualizarVideoDto(
        Long id_video,
        String titulo,
        String descricao,
        String url) {
    public AtualizarVideoDto(Video video) {
        this(video.getId_video(), video.getTitulo(), video.getDescricao(), video.getUrl());
    }
}
