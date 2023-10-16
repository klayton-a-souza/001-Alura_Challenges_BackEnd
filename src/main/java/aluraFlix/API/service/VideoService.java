package aluraFlix.API.service;

import aluraFlix.API.dto.AtualizarVideoDto;
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

        return videoRepository.save(new Video(dto));

    }

    public Video detalhar(Long id_video) {
        if(!videoRepository.existsById(id_video)){
            throw new ValidacaoException("Não foi possivel encontrar esse vídeo no banco de dados");
        }
        return videoRepository.getReferenceById(id_video);
    }

    public Video atualizacaoParcial(AtualizarVideoDto dto) {
        if(!videoRepository.existsById(dto.id_video())){
            throw new ValidacaoException("Não foi possivel encontrar esses vídeo no banco de dados");
        }

        Video video = videoRepository.getReferenceById(dto.id_video());
        video.atualizacaoParcial(dto);

        return video;
    }

    public Video atualizacao(AtualizarVideoDto dto) {
        if(!videoRepository.existsById(dto.id_video())){
            throw new ValidacaoException("Não foi possivel encontrar esses vídeo no banco de dados");
        }
        Video video = videoRepository.getReferenceById(dto.id_video());
        video.atualizacao(dto);
        return video;
    }

    public void deletar(Long id_video) {
        if(validar(id_video)){
            throw new ValidacaoException("Não foi possivel encontrar esses vídeo no banco de dados");
        }
        Video video = videoRepository.getReferenceById(id_video);
        video.deletar();
    }

    private Boolean validar(Long id_video){
        if(videoRepository.existsById(id_video)){
            return false;
        }
        return true;
    }
}
