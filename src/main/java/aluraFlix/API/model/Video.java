package aluraFlix.API.model;

import aluraFlix.API.dto.CadastrarVideoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Video(CadastrarVideoDto dto) {
        this.titulo = dto.titulo();
        this.descricao = dto.descricao();
        this.url = dto.url();
        this.ativo = true;
    }
}
