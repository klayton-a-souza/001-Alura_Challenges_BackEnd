package aluraFlix.API.controller;

import aluraFlix.API.dto.CadastrarVideoDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Video;
import aluraFlix.API.service.VideoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity <List<VideoDto>> listar(){
        List<VideoDto> videos = videoService.listar();
        return ResponseEntity.ok(videos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarVideoDto dto, UriComponentsBuilder builder){
        try {
            var uri = builder.path("/videos{id_video").buildAndExpand(dto.id_video()).toUri();
            return ResponseEntity.created(uri).body(videoService.cadastrar(dto));
        }catch (ValidacaoException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/{id_video}/")
    public ResponseEntity detalhar(@PathVariable Long id_video){
        try{
            return ResponseEntity.ok(new VideoDto(videoService.detalhar(id_video)));
        }catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }
    }
}
