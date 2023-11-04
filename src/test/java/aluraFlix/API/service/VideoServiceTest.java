package aluraFlix.API.service;

import aluraFlix.API.dto.AtualizarVideoDto;
import aluraFlix.API.dto.CadastrarVideoDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Categoria;
import aluraFlix.API.model.Video;
import aluraFlix.API.repository.CategoriaRepository;
import aluraFlix.API.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Captor
    ArgumentCaptor<Video> videoCaptor;
    @Mock
    private VideoDto videoDto;

    CadastrarVideoDto dto = new CadastrarVideoDto(
            1l,
            "Primeiro titulo",
            "Primeira descrição",
            "https://www.youtube.com/",
            1l);

        Categoria categoria = new Categoria(
                1l,
                "LIVRE",
                "#FFFFFF",
                true);
        Video video = new Video(dto,categoria);

    @Test
    @DisplayName("Devera retornar uma lista de video")
    void listarVideo(){
        //ARRANGE
        Pageable page = PageRequest.of(0,5);

        Video video1 = new Video(
                2l,
                "T: video1",
                "D: video1",
                "https://www.youtube.com/watch?",
                true,
                categoria);

        Video video2 = new Video(
                3l,
                "T: video2",
                "D: video2",
                "https://www.youtube.com/watch?",
                true,
                categoria);

        List<Video> lista = Arrays.asList(video1,video2);
        when(videoRepository.findAll(page)).thenReturn(new PageImpl<>(lista));

        //ACT
        List<VideoDto> resultado = videoService.listar(page);

        //ASSERT
        assertEquals(lista.size(),resultado.size());
        assertEquals(lista.getFirst().getTitulo(),resultado.getFirst().titulo());
    }




    @Test
    @DisplayName("Deveria retornar um video e salvar ele no banco de dados")
    void cadastrarSave(){

        //ARRANGE
        Video video = new Video(dto,categoria);
        when(videoRepository.existsByTituloOrDescricao(dto.titulo(),dto.descricao())).thenReturn(false);
        when(categoriaRepository.getReferenceById(dto.id_categoria())).thenReturn(categoria);

        //ACT
        videoService.cadastrar(dto);

        //ASSERT
        verify(videoRepository).existsByTituloOrDescricao(dto.titulo(),dto.descricao());
        verify(categoriaRepository).getReferenceById(dto.id_categoria());
        verify(videoRepository).save(videoCaptor.capture());
    }
    @Test
    @DisplayName("Deveria retornar um mensagem informando que esse video que esta tentando cadastrar ja esta cadastrado")
    void cadastrarJaCadastrado(){

        //ARRANGE + ACT
        when(videoRepository.existsByTituloOrDescricao(dto.titulo(),dto.descricao())).thenReturn(true);

        //ASSERT
        assertThrows(ValidacaoException.class,() -> videoService.cadastrar(dto));
    }

    @Test
    @DisplayName("Deve retornar um video ao executar uma busca pelo id")
    void detalhar(){
        //ARRANGE
        when(videoRepository.existsById(dto.id_video())).thenReturn(true);
        when(videoRepository.getReferenceById(dto.id_video())).thenReturn(videoCaptor.capture());

        //ACT
        videoService.detalhar(dto.id_video());

        //ASSERT
        verify(videoRepository).existsById(dto.id_video());
        verify(videoRepository).getReferenceById(dto.id_video());
    }

    @Test
    @DisplayName("Deveria verificar se o valor que esta sendo mandado no dto esta alterando na entidade")
    void atualizacaoParcial() {
        AtualizarVideoDto dtoAtualizacao = new AtualizarVideoDto(1l, "Novo titulo", null, null);

        given(videoRepository.existsById(dtoAtualizacao.id_video())).willReturn(true);
        given(videoRepository.getReferenceById(dtoAtualizacao.id_video())).willReturn(video);

        videoService.atualizacaoParcial(dtoAtualizacao);

        assertEquals(video.getTitulo(),dtoAtualizacao.titulo());
        assertNotEquals(video.getDescricao(),dtoAtualizacao.descricao());
    }

    @Test
    @DisplayName("Deveria verificar se o valor que esta sendo mandado no dto esta alterando na entidade")
    void atualizacaoTotal() {
        AtualizarVideoDto dtoAtualizacao = new AtualizarVideoDto(1l, "Novo titulo", "Nova descrição", "https://www.youtube.com/watch");

        given(videoRepository.existsById(dtoAtualizacao.id_video())).willReturn(true);
        given(videoRepository.getReferenceById(dtoAtualizacao.id_video())).willReturn(video);

        videoService.atualizacao(dtoAtualizacao);

        assertEquals(video.getTitulo(),dtoAtualizacao.titulo());
        assertEquals(video.getDescricao(),dtoAtualizacao.descricao());
        assertEquals(video.getUrl(),dtoAtualizacao.url());
    }

    @Test
    @DisplayName("Deveria mudar o status do video de ativo para false")
    void deletar(){

        Long id_video = 1l;
        given(videoRepository.existsById(id_video)).willReturn(true);
        given(videoRepository.getReferenceById(id_video)).willReturn(video);

        videoService.deletar(id_video);

        assertEquals(video.getAtivo(),false);


    }
}