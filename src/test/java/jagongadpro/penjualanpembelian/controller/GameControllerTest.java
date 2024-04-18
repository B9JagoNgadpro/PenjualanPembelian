package jagongadpro.penjualanpembelian.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.WebResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
@AutoConfigureMockMvc
@SpringBootTest
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testCreateGameSuccess() throws Exception {
        CreateGameRequest request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setStok(10);
        request.setHarga(10000);
        mockMvc.perform(post("/api/games").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertEquals("OK", response.getData());
                });
    }

    @Test
    public void testCreateGameFailed() throws Exception {
        CreateGameRequest request = new CreateGameRequest();
        request.setDeskripsi("deskripsi");
        request.setHarga(10000);
        request.setStok(100);
        mockMvc.perform(post("/api/games").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isBadRequest())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertEquals("nama: must not be blank", response.getErrors());
                });
    }

    @Test
    public void testGetGame() throws Exception {
        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));

    }

    @Test
    public void testFilterGame() throws Exception {
        mockMvc.perform(get("/api/games").param("name", "ABC"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));

    }
}
