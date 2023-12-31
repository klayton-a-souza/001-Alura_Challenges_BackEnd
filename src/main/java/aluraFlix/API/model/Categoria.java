package aluraFlix.API.model;

import aluraFlix.API.dto.AtualizacaoCategoriaDto;
import aluraFlix.API.dto.CadastrarCategoriaDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "categorias")
@Entity(name = "Categoria")

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Categoria {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_categoria;
    private String titulo;
    private String cor;
    private Boolean ativo;

    public Categoria(CadastrarCategoriaDto dto) {
        this.titulo = dto.titulo();
        this.cor = dto.cor();
        this.ativo = true;
    }

    public void atualizacaoParcial(AtualizacaoCategoriaDto dto) {
        if(dto.titulo() != null){
            this.titulo = dto.titulo();
        }
        if(dto.cor() != null){
            this.cor = dto.cor();
        }
    }

    public void atualizacao(AtualizacaoCategoriaDto dto) {
        this.titulo = dto.titulo();
        this.cor = dto.cor();
    }

    public void deletar() {
        this.ativo = false;
    }
}
