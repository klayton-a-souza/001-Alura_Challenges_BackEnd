package aluraFlix.API.controller;

import aluraFlix.API.dto.CadastrarCategoriaDto;
import aluraFlix.API.dto.CategoriaDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.service.CategoriaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
            var categoria = categoriaService.detalhar(id_categoria);
            return ResponseEntity.ok(new CategoriaDto(categoria));
        }catch (ValidacaoException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity <List<CategoriaDto>> listar(){
        List<CategoriaDto> categorias = categoriaService.listar();
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
}
