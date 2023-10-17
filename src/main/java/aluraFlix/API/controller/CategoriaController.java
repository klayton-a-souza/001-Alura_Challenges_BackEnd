package aluraFlix.API.controller;

import aluraFlix.API.dto.CategoriaDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")

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
}
