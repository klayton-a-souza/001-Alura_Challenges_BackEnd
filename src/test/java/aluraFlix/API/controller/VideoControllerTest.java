package aluraFlix.API.controller;

import aluraFlix.API.dto.CadastrarVideoDto;
import aluraFlix.API.dto.VideoDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
}