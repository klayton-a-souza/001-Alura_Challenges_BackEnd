package aluraFlix.API.dto;

import aluraFlix.API.model.Categoria;

public record CategoriaDto(Long id_categoria, String titulo, String cor, Boolean ativo) {

    public CategoriaDto(Categoria categoria) {
        this(categoria.getId_categoria(), categoria.getTitulo(), categoria.getCor(), categoria.getAtivo());
    }
}
