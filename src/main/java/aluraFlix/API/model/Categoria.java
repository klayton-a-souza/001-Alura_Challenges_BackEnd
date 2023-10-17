package aluraFlix.API.model;

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

}
