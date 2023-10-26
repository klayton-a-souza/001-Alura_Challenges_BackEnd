package aluraFlix.API.service;

import aluraFlix.API.dto.AtualizacaoCategoriaDto;
import aluraFlix.API.dto.CadastrarCategoriaDto;
import aluraFlix.API.dto.CategoriaDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Categoria;
import aluraFlix.API.repository.CategoriaRepository;
import aluraFlix.API.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private VideoRepository videoRepository;

    public Categoria detalhar(Long id_categoria) {

        if(!categoriaRepository.existsById(id_categoria)){
            throw new ValidacaoException("Essa categoria não esta cadastrada no banco de dados!");
        }

        return categoriaRepository.getReferenceById(id_categoria);

    }

    public Categoria cadastrar(CadastrarCategoriaDto dto) {
        boolean jaCadastrado = categoriaRepository.existsByTitulo(dto.titulo());

        if(jaCadastrado){
            throw new ValidacaoException("Essa categoria já esta cadastrada no banco de dados!");
        }

        Categoria categoria = new Categoria(dto);

        return categoriaRepository.save(categoria);
    }

    public List<CategoriaDto> listar(Pageable paginacao) {
        return categoriaRepository
                .findAll(paginacao)
                .stream()
                .map(CategoriaDto::new)
                .toList();
    }

    public Categoria atualizacaoParcial(AtualizacaoCategoriaDto dto) {
        validar(dto.id_categoria());

        Categoria categoria = categoriaRepository.getReferenceById(dto.id_categoria());
        categoria.atualizacaoParcial(dto);
        return categoria;
    }

    public Categoria atualizacao(AtualizacaoCategoriaDto dto) {
        validar(dto.id_categoria());

        Categoria categoria = categoriaRepository.getReferenceById(dto.id_categoria());
        categoria.atualizacao(dto);
        return categoria;
    }

    public void deletar(Long id_categoria) {
        validar(id_categoria);

        Categoria categoria = categoriaRepository.getReferenceById(id_categoria);
        categoria.deletar();
    }

    private void validar(Long id_categoria) {
        if(!categoriaRepository.existsById(id_categoria)){
            throw new ValidacaoException("Essa categoria não esta cadastrada no banco de dados!");
        }
    }

    public List<VideoDto> videoPorCategoria(Long id_categoria) {
        List<VideoDto> lista = videoRepository.bucasrVideoPorCategoria(id_categoria);
        return lista;
    }
}
