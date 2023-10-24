package aluraFlix.API.model;

import aluraFlix.API.dto.AtualizarVideoDto;
import aluraFlix.API.dto.CadastrarVideoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Video")
@Table(name = "videos")

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Video {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_video;
    private String titulo;
    private String descricao;
    private String url;
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public Video(CadastrarVideoDto dto) {
        this.titulo = dto.titulo();
        this.descricao = dto.descricao();
        this.url = dto.url();
        this.ativo = true;
    }

    public Video(CadastrarVideoDto dto, Categoria categoria) {
        this.titulo = dto.titulo();
        this.descricao = dto.descricao();
        this.url = dto.url();
        this.ativo = true;
        this.categoria = categoria;
    }

    public void atualizacaoParcial(AtualizarVideoDto dto) {
        if(dto.titulo() != null){
            this.titulo = dto.titulo();
        }
        if(dto.descricao() != null){
            this.descricao = dto.descricao();
        }
        if(dto.url() != null){
            this.url = dto.url();
        }
    }

    public void atualizacao(AtualizarVideoDto dto) {
        this.titulo = dto.titulo();
        this.descricao = dto.descricao();
        this.url = dto.url();
    }

    public void deletar() {
        this.ativo = false;
    }
}
