package aluraFlix.API.service;

import aluraFlix.API.dto.AtualizacaoCategoriaDto;
import aluraFlix.API.dto.CadastrarCategoriaDto;
import aluraFlix.API.dto.CategoriaDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Categoria;
import aluraFlix.API.model.Video;
import aluraFlix.API.repository.CategoriaRepository;
import aluraFlix.API.repository.VideoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private VideoRepository videoRepository;
    @Captor
    private ArgumentCaptor<Categoria> categoriaCaptor;

    Categoria categoria = new Categoria(
            1l,
            "LIVRE",
            "#FFFFFF",
            true);

    @Test
    @DisplayName("Detalhar uma categoria passando o ID como parametro de busca")
    void detalhar(){
        Long id_categoria = 1l;

        when(categoriaRepository.existsById(id_categoria)).thenReturn(true);
        when(categoriaRepository.getReferenceById(id_categoria)).thenReturn(categoriaCaptor.capture());

        categoriaService.detalhar(id_categoria);

        verify(categoriaRepository).existsById(id_categoria);
        verify(categoriaRepository).getReferenceById(id_categoria);

    }

    @Test
    @DisplayName("Cadastrar uma nova categoria no banco de dados com sucesso")
    void cadastrarCategoria01(){

        //ARRANGE
        CadastrarCategoriaDto dto = new CadastrarCategoriaDto(1l,"LIVRE","#FFFFFF",true);
        when(categoriaRepository.existsByTitulo(dto.titulo())).thenReturn(false);

        //ACT
        categoriaService.cadastrar(dto);

        //ASSERT
        verify(categoriaRepository).existsByTitulo(dto.titulo());
        verify(categoriaRepository).save(categoriaCaptor.capture());
    }

    @Test
    @DisplayName("Cadastrar uma categoria que ja esta salva no banco de dados")
    void cadastrarCategoria02(){
        //ARRANGE
        CadastrarCategoriaDto dto = new CadastrarCategoriaDto(1l,"LIVRE","#FFFFFF",true);

        //ACT
        when(categoriaRepository.existsByTitulo(dto.titulo())).thenReturn(true);

        //ASSERT
        assertThrows(ValidacaoException.class,()->categoriaService.cadastrar(dto));
    }

    @Test
    @DisplayName("Listar todas categorias no banco de dados, com pageable passado como parametro")
    void listarCategorias01(){

        //ARRANGE
        Pageable page = PageRequest.of(0,5);
        Categoria categoria1 = new Categoria(
                2l,
                "T: categoria1",
                "#FF0000",
                true);
        Categoria categoria2 = new Categoria(
                3l,
                "T: categoria2",
                "#008000",
                true);
        List<Categoria> categoriaList = Arrays.asList(categoria1,categoria2);
        when(categoriaRepository.findAll(page)).thenReturn(new PageImpl<>(categoriaList));

        //ACT
        List<CategoriaDto> resultado = categoriaService.listar(page);

        //ASSERT
        assertEquals(categoriaList.size(),resultado.size());
        assertEquals(categoriaList.getFirst().getTitulo(),resultado.getFirst().titulo());
    }

    @Test
    @DisplayName("Ao realizar a atualização parcial, verificar se o titulo foi alterado")
    void atualizacaoParcial01(){
        AtualizacaoCategoriaDto dto = new AtualizacaoCategoriaDto(1l,"Nova categoria",null,true);

        given(categoriaRepository.existsById(dto.id_categoria())).willReturn(true);
        given(categoriaRepository.getReferenceById(dto.id_categoria())).willReturn(categoria);

        categoriaService.atualizacaoParcial(dto);

        assertEquals(categoria.getTitulo(),dto.titulo());
        assertNotEquals(categoria.getCor(),dto.cor());
    }

    @Test
    @DisplayName("Verificar se os paramentos: titulo,cor e ativo são os mesmo tanto no dto quando na categoria")
    void atualizacaoTotal(){
        //ARRANGE
        AtualizacaoCategoriaDto dto = new AtualizacaoCategoriaDto(
                1l,
                "Nova categoria",
                "#FF0000",
                true);

        given(categoriaRepository.existsById(dto.id_categoria())).willReturn(true);
        given(categoriaRepository.getReferenceById(dto.id_categoria())).willReturn(categoria);

        //ACT
        categoriaService.atualizacao(dto);

        //ASSERT
        assertEquals(categoria.getTitulo(),dto.titulo());
        assertEquals(categoria.getCor(),dto.cor());
        assertEquals(categoria.getAtivo(),dto.ativo());

    }

    @Test
    @DisplayName("Verificar se o parametro (ativo) foi alterado")
    void deletarCategoria(){
        Long id_categoria = 1l;
        given(categoriaRepository.existsById(id_categoria)).willReturn(true);
        given(categoriaRepository.getReferenceById(id_categoria)).willReturn(categoria);

        categoriaService.deletar(id_categoria);

        assertEquals(categoria.getAtivo(),false);
    }

    @Test
    @DisplayName("Listar videos que tem a mesma categoria, para achar esses videos e necessario passar um id da categoria")
    void videoCategoria(){

        Video video2 = new Video(
                3l,
                "T: video2",
                "D: video2",
                "https://www.youtube.com/watch?",
                true,
                categoria);

        VideoDto dto2 = new VideoDto(2l,
                "T: video2",
                "D: video2",
                "https://www.youtube.com/watch?",
                true,
                1l);
        VideoDto dto3 = new VideoDto(3l,
                "T: video3",
                "D: video3",
                "https://www.youtube.com/watch?",
                true,
                1l);
        List<VideoDto> listaDto = Arrays.asList(dto2,dto3);
        when(videoRepository.bucasrVideoPorCategoria(1l)).thenReturn(listaDto);

        List<VideoDto> resultado = categoriaService.videoPorCategoria(1l);

        assertEquals(listaDto.size(),resultado.size());
        assertEquals(listaDto.getFirst().titulo(),resultado.getFirst().titulo());
    }
}