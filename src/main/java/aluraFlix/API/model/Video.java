package aluraFlix.API.model;

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

}
