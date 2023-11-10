package aluraFlix.API.controller;

import aluraFlix.API.dto.AtualizacaoCategoriaDto;
import aluraFlix.API.service.CategoriaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @MockBean
    private CategoriaService categoriaService;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Codigo 200: Para detalhar uma categoria pelo ID que esta no banco de dados!")
    void detalharCategoria01() throws Exception {
        Long id_categoria = 1l;
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias/{id_categoria}/",id_categoria)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

    @Test
    @DisplayName("Codigo 200: Para listar todas categorias cadastradas!")
    void listarCategorias01() throws Exception{
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias/")
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

    @Test
    void cadastrarCategoria01() throws Exception{
        String json =
                """
                {
                    "titulo":"TESTE",
                    "cor":"#FFFFFF"
                }
                """;

        MockHttpServletResponse response = mockMvc.perform(
                post("/categorias/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201,response.getStatus());
    }

    @Test
    @DisplayName("Codigo 200: Para requisição que tenta uma atualização parcial de uma categoria cadastrado no banco de dados")
    void atualizacaoParcial01() throws Exception {
        String json =
                """
                {
                    "titulo":"Titulo Patch"
                }
                """;
        MockHttpServletResponse response = mockMvc.perform(
                patch("/categorias/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());

    }

    @Test
    @DisplayName("Codigo 200: Atualização total de um dado no banco de dados")
    void atualizacaoTotal() throws Exception {
        String json =
                """
                {
                    "titulo":"Titulo Put",
                    "cor":"#FF0000"
                }
                """;
        MockHttpServletResponse response = mockMvc.perform(
                put("/categorias/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

    @Test
    @DisplayName("Codigo 204: Para requisição que exetuda a exclusão logica de uma categoria com sucesso")
    void deletatCategoria() throws Exception{
        Long id_categoria = 1l;

        MockHttpServletResponse response = mockMvc.perform(
                delete("/categorias/{id_categoria}/",id_categoria)
        ).andReturn().getResponse();

        assertEquals(204,response.getStatus());
    }

    @Test
    @DisplayName("Codigo 200: Para listar todas categorias cadastradas!")
    void videoPorCategoria01() throws Exception{
        Long id_categoria = 1l;
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias/{id_categoria}/videos",id_categoria)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }


}