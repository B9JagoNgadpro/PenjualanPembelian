package jagongadpro.penjualanpembelian.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.FilterGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;
import jagongadpro.penjualanpembelian.dto.WebResponse;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jagongadpro.penjualanpembelian.service.GameService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(GameController.class)
@ExtendWith(MockitoExtension.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    GameService gameService;

    @Test
    void testCreateGameSuccess() throws Exception {
        CreateGameRequest request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setStok(10);
        request.setHarga(10000);
        when(gameService.create(any(CreateGameRequest.class))).thenReturn(GameResponse.builder().nama("Game 1").deskripsi("deskripsi").stok(10).harga(10000).build());
        mockMvc.perform(post("/api/games/create").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)).header("Authorization", "Bearer token"))
                .andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getErrors());
                    assertEquals("Game 1", response.getData().getNama());
                    assertEquals("deskripsi", response.getData().getDeskripsi());
                    assertEquals(10, response.getData().getStok());
                    assertEquals(10000, response.getData().getHarga());
                });
    }

    @Test
     void testCreateGameFailed() throws Exception {
        CreateGameRequest request = new CreateGameRequest();
        request.setDeskripsi("deskripsi");
        request.setHarga(10000);
        request.setStok(100);
        Set<CreateGameRequest> set = new HashSet<>();
        set.add(request);
        ConstraintViolation<?> mockViolation = mock(ConstraintViolation.class);

        when(mockViolation.getMessage()).thenReturn("nama: must not be blank");

        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mockViolation);


        ConstraintViolationException exception = new ConstraintViolationException(violations);


        when(gameService.create(any(CreateGameRequest.class))).thenThrow(exception);
        mockMvc.perform(post("/api/games/create").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)).header("Authorization", "Bearer Token"))
                .andExpectAll(status().isBadRequest())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals("null: nama: must not be blank", response.getErrors());
                });
    }

    @Test
     void testGetAllGame() throws Exception {
        ArrayList<GameResponse> games = new ArrayList();
        games.add(GameResponse.builder().build());
        games.add(GameResponse.builder().build());

        when(gameService.getAll()).thenReturn(games);

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
            assertEquals(2, response.getData().size());
        });

    }

    @Test
     void testFilterGameNotFound() throws Exception {
        ArrayList<GameResponse> games = new ArrayList();
        when(gameService.filter(any(FilterGameRequest.class))).thenReturn(games);
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
        ArrayList<GameResponse> games = new ArrayList();
        games.add(GameResponse.builder().nama("Game 1").build());
        games.add(GameResponse.builder().nama("Game 2").build());
        when(gameService.filter(any(FilterGameRequest.class))).thenReturn(games);
        when(gameService.getAll()).thenReturn(games);
        mockMvc.perform(get("/api/games/get").param("nama", "Game").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(2, response.getData().size());

                });
        mockMvc.perform(get("/api/games/get").param("harga", String.valueOf(1000)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(2, response.getData().size());

                });
        mockMvc.perform(get("/api/games/get").param("kategori", "act").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<List<GameResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(2, response.getData().size());

                });
        for (int i = 0; i < 5; i++) {

        }
    }

    @Test
     void getByIdSuccess() throws  Exception{
        Game game = new Game.GameBuilder().nama("test").build();
        when(gameService.getById(anyString())).thenReturn(GameResponse.builder().nama("test").build());
        mockMvc.perform(get("/api/games/"+"id").contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
                    assertNotNull(response.getData());
                    assertNull(response.getErrors());
                    assertEquals("test", response.getData().getNama());
                });

    }
    @Test
     void getByIdNotFound() throws  Exception{
        when(gameService.getById("abc")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,"Game tidak ditemukan"));
        mockMvc.perform(get("/api/games/abc").contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isNotFound())
                .andDo(result -> {
                    WebResponse<GameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
                    assertNull(response.getData());
                    assertNotNull(response.getErrors());
                    assertEquals("Game tidak ditemukan", response.getErrors() );
                });
    }

}
