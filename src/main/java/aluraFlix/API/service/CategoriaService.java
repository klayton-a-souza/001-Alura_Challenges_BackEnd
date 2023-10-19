package aluraFlix.API.service;

import aluraFlix.API.dto.AtualizacaoCategoriaDto;
import aluraFlix.API.dto.CadastrarCategoriaDto;
import aluraFlix.API.dto.CategoriaDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Categoria;
import aluraFlix.API.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

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

    public List<CategoriaDto> listar() {
        return categoriaRepository
                .findAll()
                .stream()
                .map(CategoriaDto::new)
                .toList();
    }

    public Categoria atualizacaoParcial(AtualizacaoCategoriaDto dto) {
        if(!categoriaRepository.existsById(dto.id_categoria())){
            throw new ValidacaoException("Essa categoria não esta cadastrada no banco de dados!");
        }
        Categoria categoria = categoriaRepository.getReferenceById(dto.id_categoria());
        categoria.atualizacaoParcial(dto);
        return categoria;
    }
}
