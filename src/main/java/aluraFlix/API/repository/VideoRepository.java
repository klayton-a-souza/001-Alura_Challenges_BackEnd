package aluraFlix.API.repository;

import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.model.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.util.List;

public interface VideoRepository extends JpaRepository<Video,Long> {
    boolean existsByTituloOrDescricao(String titulo, String descricao);

    @Query("""
            select v from Video v
            where
            v.categoria.id_categoria = :id_categoria
            """)
    List<VideoDto> bucasrVideoPorCategoria(Long id_categoria);
}
