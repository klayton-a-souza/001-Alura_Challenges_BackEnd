package aluraFlix.API.dto;

import aluraFlix.API.model.Categoria;

public record AtualizacaoCategoriaDto(Long id_categoria, String titulo, String cor, Boolean ativo) {
    public AtualizacaoCategoriaDto(Categoria categoria) {
        this(categoria.getId_categoria(), categoria.getTitulo(), categoria.getCor(), categoria.getAtivo());
    }
}
