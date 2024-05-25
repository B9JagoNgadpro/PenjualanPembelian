package jagongadpro.penjualanpembelian.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;
import jagongadpro.penjualanpembelian.dto.WebResponse;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private  GameRepository gameRepository;
    @BeforeEach
    void setUp() {
        gameRepository.deleteAll();
    }
    @Test
    void testCreateGameSuccess() throws Exception {
        CreateGameRequest request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setStok(10);
        request.setHarga(10000);
        mockMvc.perform(post("/api/games/create").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)).header("Authorization", "Bearer token"))
                .andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getErrors());
                    assertEquals("Game1", response.getData().getNama());
                    assertEquals("deskripsi", response.getData().getDeskripsi());
                    assertEquals(10, response.getData().getStok());
                    assertEquals(10000, response.getData().getHarga());

                    assertTrue(gameRepository.existsById(response.getData().getId()));
                });
    }

    @Test
     void testCreateGameFailed() throws Exception {
        CreateGameRequest request = new CreateGameRequest();
        request.setDeskripsi("deskripsi");
        request.setHarga(10000);
        request.setStok(100);
        mockMvc.perform(post("/api/games/create").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)).header("Authorization", "Bearer Token"))
                .andExpectAll(status().isBadRequest())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals("nama: must not be blank", response.getErrors());
                });
    }

    @Test
     void testGetAllGame() throws Exception {
        for (int i = 0; i < 5; i++) {
            Game game = new Game.GameBuilder().nama("Game "+ i).stok(10).harga(1000).kategori("action").deskripsi("bagus").build();
            gameRepository.save(game);
        }

        mockMvc.perform(
                get("/api/games/get-all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(5, response.getData().size());
        });

    }

    @Test
     void testFilterGameNotFound() throws Exception {

        mockMvc.perform(get("/api/games/get").param("name", "ABC").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(0, response.getData().size());

                });
    }

    @Test
     void testFilterGameSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            Game game = new Game.GameBuilder().nama("Game "+ i).stok(10).harga(1000).kategori("action").deskripsi("bagus").build();
            gameRepository.save(game);
        }
        mockMvc.perform(get("/api/games/get").param("nama", "Game").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(10, response.getData().size());

                });
        mockMvc.perform(get("/api/games/get").param("harga", String.valueOf(1000)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(10, response.getData().size());

                });
        mockMvc.perform(get("/api/games/get").param("kategori", "act").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(10, response.getData().size());

                });
    }

    @Test
     void getByIdSuccess() throws  Exception{
        Game game = new Game.GameBuilder().nama("test").build();
        gameRepository.save(game);
        String id = game.getId();
        mockMvc.perform(get("/api/games/"+id).contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
                    assertNotNull(response.getData());
                    assertNull(response.getErrors());
                    assertEquals("test", response.getData().getNama());
                });
    }
    @Test
     void getByIdNotFound() throws  Exception{

        mockMvc.perform(get("/api/games/abc").contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isNotFound())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
                    assertNull(response.getData());
                    assertNotNull(response.getErrors());
                    assertEquals("Game tidak ditemukan", response.getErrors() );
                });
    }

}
