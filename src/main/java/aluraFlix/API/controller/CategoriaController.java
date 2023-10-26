package aluraFlix.API.controller;

import aluraFlix.API.dto.AtualizacaoCategoriaDto;
import aluraFlix.API.dto.CadastrarCategoriaDto;
import aluraFlix.API.dto.CategoriaDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.service.CategoriaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/categorias/")

public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{id_categoria}/")
    public ResponseEntity detalhar(@PathVariable Long id_categoria){
        try{
            return ResponseEntity.ok(new CategoriaDto(categoriaService.detalhar(id_categoria)));
        }catch (ValidacaoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity <List<CategoriaDto>> listar(@PageableDefault (size = 1) Pageable paginacao){
        List<CategoriaDto> categorias = categoriaService.listar(paginacao);
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarCategoriaDto dto,
                                    UriComponentsBuilder builder){
        try{
            var uri = builder.path("/categorias/{id_categoria}/")
                    .buildAndExpand(dto.id_categoria()).toUri();
            return ResponseEntity.created(uri).body(categoriaService.cadastrar(dto));
        }catch (ValidacaoException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PatchMapping
    @Transactional
    public ResponseEntity atualizacaoParcial(@RequestBody @Valid AtualizacaoCategoriaDto dto){
        try {
            return ResponseEntity.ok(new AtualizacaoCategoriaDto(categoriaService.atualizacaoParcial(dto)));
        }catch (ValidacaoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizacao(@RequestBody @Valid AtualizacaoCategoriaDto dto){
        try {
            return ResponseEntity.ok(new AtualizacaoCategoriaDto(categoriaService.atualizacao(dto)));
        }catch (ValidacaoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id_categoria}/")
    @Transactional
    public ResponseEntity deletar(@PathVariable @Valid Long id_categoria){
        try {
            categoriaService.deletar(id_categoria);
            return ResponseEntity.noContent().build();
        }catch (ValidacaoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("{id_categoria}/videos")
    public ResponseEntity <List<VideoDto>> videoPorCategoria(@PathVariable Long id_categoria){
        return ResponseEntity.ok(categoriaService.videoPorCategoria(id_categoria));
    }


}
