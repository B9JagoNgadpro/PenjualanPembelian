package jagongadpro.penjualanpembelian.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
@AutoConfigureMockMvc
@SpringBootTest
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateGame() throws Exception {
        mockMvc.perform(post("/api/games"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));

    }

    @Test
    public void testGetGame() throws Exception {
        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));

    }
}
