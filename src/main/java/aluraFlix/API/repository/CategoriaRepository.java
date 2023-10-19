package aluraFlix.API.repository;

import aluraFlix.API.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    boolean existsByTitulo(String titulo);
}
