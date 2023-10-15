package aluraFlix.API.service;

import aluraFlix.API.dto.CadastrarVideoDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Video;
import aluraFlix.API.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<VideoDto> listar(){
        return videoRepository
                .findAll()
                .stream()
                .map(VideoDto::new)
                .toList();
    }

    public Video cadastrar(CadastrarVideoDto dto) {
        boolean jaCadastrado = videoRepository.existsByTituloOrDescricao(dto.titulo(),dto.descricao());

        if(jaCadastrado){
            throw new ValidacaoException("Dados já cadastrado no banco de dados!");
        }

        Video video = new Video(dto);
        videoRepository.save(video);

        return video;
    }
}
