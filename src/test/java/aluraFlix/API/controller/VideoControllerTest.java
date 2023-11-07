package aluraFlix.API.controller;

import aluraFlix.API.dto.AtualizarVideoDto;
import aluraFlix.API.dto.CadastrarVideoDto;
import aluraFlix.API.exception.ValidacaoException;
import aluraFlix.API.model.Categoria;
import aluraFlix.API.model.Video;
import aluraFlix.API.repository.CategoriaRepository;
import aluraFlix.API.repository.VideoRepository;
import aluraFlix.API.service.VideoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class VideoControllerTest {

    @MockBean
    private VideoService videoService;
    @Autowired
    MockMvc mockMvc;
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
    @DisplayName("Codigo 200: Para listar todos os videos cadastrado")
    void listarTodosOsVideos() throws Exception {
        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/videos")
        ).andReturn().getResponse();
        //ASSERT
        assertEquals(200,response.getStatus());

    }

    @Test
    @DisplayName("Codigo 201: Para cadastro de video com sucesso")
    void cadastrarVideo01() throws Exception {
        String json =
                """
                {
                    "titulo":"primeiro titulo",
                    "descricao":"primeira descrição",
                    "url":"https://www.youtube.com/watch?",
                    "id_categoria":1                      
                }                
                """;

        MockHttpServletResponse response = mockMvc.perform(
                post("/videos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201,response.getStatus());
    }
    @Test
    @DisplayName("Codigo 400: Para cadastro de video com parametro errado")
    void cadastrarVideo02() throws Exception {
        String json =
                """
                {
                    "titulo":"primeiro titulo",
                    "descricao":"primeira descrição",
                    "url":"url invalida",
                    "id_categoria":1                      
                }                
                """;

        MockHttpServletResponse response = mockMvc.perform(
                post("/videos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400,response.getStatus());
    }

    @Test
    @DisplayName("Codigo 200: Para detalhar um video pelo ID que esta cadastrado no banco de dados")
    void detalharVideo01() throws Exception {
        Long id_video = 1l;
        MockHttpServletResponse response = mockMvc.perform(
                get("/videos/{id_video}/",id_video)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());

    }

    @Test
    @DisplayName("Codigo 404: Para detalhar um video pelo ID que não esta cadastrado no banco de dados")
    void detalharVideo02() throws Exception {
        Long id_video = 1l;
        given(videoService.detalhar(id_video)).willThrow(ValidacaoException.class);

        MockHttpServletResponse response = mockMvc.perform(
                get("/videos/{id_video}/",id_video)
        ).andReturn().getResponse();

        assertEquals(404,response.getStatus());
    }

    @Test
    @DisplayName("Codigo 200: Para requisição que tenta uma atualização parcial de um video cadastrado no banco de dados")
    void patchVideo01() throws Exception {
        String json =
                """
                {
                    "titulo":"Titulo Patch"
                }
                """;
        MockHttpServletResponse response = mockMvc.perform(
                patch("/videos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }
    //PATCH - Não estou conseguindo forçar lançar a exception

    @Test
    @DisplayName("Codigo 200: Atualização total de um dado no banco de dados")
    void putVideo01() throws Exception{

        String json =
                """
                {
                    "id_video":1,
                    "titulo":"titulo put",
                    "descricao":"descricao put",
                    "url":"https://www.crunchyroll.com/"
                }
                """;
        MockHttpServletResponse response = mockMvc.perform(
                put("/videos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

    //PUT - Não estou consaeguindo lançar a exception

    @Test
    @DisplayName("Codigo 204: Para requisição que executa a exclusão logica com sucesso")
    void deleteVideo() throws Exception {
        Long id_video = 1l;
        MockHttpServletResponse response = mockMvc.perform(
                delete("/videos/{id_video}/",id_video)
        ).andReturn().getResponse();

        assertEquals(204,response.getStatus());
    }


    }