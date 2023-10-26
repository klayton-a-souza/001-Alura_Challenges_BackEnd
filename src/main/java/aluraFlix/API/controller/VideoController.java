package aluraFlix.API.controller;

import aluraFlix.API.dto.AtualizarVideoDto;
import aluraFlix.API.dto.CadastrarVideoDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Video;
import aluraFlix.API.service.VideoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/videos")

public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping
    public ResponseEntity <List<VideoDto>> listar(@PageableDefault (size = 5) Pageable paginacao){
        List<VideoDto> videos = videoService.listar(paginacao);
        return ResponseEntity.ok(videos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarVideoDto dto, UriComponentsBuilder builder){
        try {
            var uri = builder.path("/videos{id_video").buildAndExpand(dto.id_video()).toUri();
            videoService.cadastrar(dto);
            return ResponseEntity.created(uri).body("Vídeo cadastrado com sucesso!");
        }catch (ValidacaoException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/{id_video}/")
    public ResponseEntity detalhar(@PathVariable Long id_video){
        try{
            Video video = videoService.detalhar(id_video);
            return ResponseEntity.ok(new VideoDto(video));

        }catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping
    @Transactional
    public ResponseEntity atualizacaoParcial(@RequestBody @Valid AtualizarVideoDto dto){
        try{
            return ResponseEntity.ok(new AtualizarVideoDto(videoService.atualizacaoParcial(dto)));
        }catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizacao(@RequestBody @Valid AtualizarVideoDto dto){
        try{
            return ResponseEntity.ok(new AtualizarVideoDto(videoService.atualizacao(dto)));
        }catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_video}/")
    @Transactional
    public ResponseEntity deletar(@PathVariable @Valid Long id_video){
        try {
            videoService.deletar(id_video);
            return ResponseEntity.noContent().build();
        }catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity videoPorTitulo(@RequestParam(value ="search") String titulo){
        try{
            return ResponseEntity.ok(new VideoDto(videoService.buscarPeloTitulo(titulo)));
        }catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }

    }
}
