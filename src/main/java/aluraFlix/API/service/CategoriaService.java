package aluraFlix.API.service;

import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Categoria;
import aluraFlix.API.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
