package aluraFlix.API.controller;

import aluraFlix.API.dto.AtualizarVideoDto;
import aluraFlix.API.dto.CadastrarVideoDto;
import aluraFlix.API.dto.VideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Video;
import aluraFlix.API.repository.VideoRepository;
import aluraFlix.API.service.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@SpringBootTest
@AutoConfigureMockMvc

class VideoControllerTest {

    @MockBean
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private Video video;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CadastrarVideoDto cadastrarVideoDto;
    @Mock
    private VideoDto videoDto;
    @Mock
    private AtualizarVideoDto atualizarVideoDto;


    @Test
    @DisplayName("Deveria devolver codigo 200 para requisições para listar todos os videos")
    void cenarioListar01() throws Exception {

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/videos")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 201 para requisições que cadastra um novo video")
    void cenarioCadastrar01() throws Exception {
        //ARRANGE
        String json = """
                {
                    "titulo": "titlo teste",
                    "descricao": "descricao teste",
                    "url": "https://youtu.be/Y1HCO2v_fp4"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/videos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201,response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 400 para requisições que tenta cadastrar com dados invalidos")
    void cenarioCadastrar02() throws Exception {
        //ARRANGE
        String json = """
                {
                    "titulo": "",
                    "descricao": "descricao teste",
                    "url": "https://youtu.be/Y1HCO2v_fp4"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/videos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400,response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar código 200 para requisições que tentam detalhar um vídeo específico pelo id")
    void cenarioDetalhar01() throws Exception {

        Long id_video = 1l;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/videos/{id_video}/",id_video)
        ).andReturn().getResponse();
        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar código 404 quando o vídeo que deveria ser detalhado não e encontrado no banco de dados")
    void cenarioDetalhar02() throws Exception {
        Long id_video = 1l;
        given(videoService.detalhar(id_video)).willThrow(ValidacaoException.class);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/videos/{id_video}/",id_video)

        ).andReturn().getResponse();
        //ASSERT
        assertEquals(404,response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar código 200, quando a atulização parcial e realizada com sucesso")
    void cenarioAtualizacaoParcial01() throws Exception {
        String json = """
                {
                    "titulo": "titlo teste",
                    "descricao": "descricao teste",
                    
                }
                """;
        MockHttpServletResponse response = mockMvc.perform(
                patch("/videos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

}