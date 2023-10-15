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
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarVideoDto dto
            ,UriComponentsBuilder uriComponentsBuilder){
        try {

            Video video = videoService.cadastrar(dto);

            var uri = uriComponentsBuilder.path("/videos{id_video}").buildAndExpand(video.getId_video()).toUri();
            return ResponseEntity.created(uri).body(new CadastrarVideoDto(video));

        }catch (ValidacaoException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
