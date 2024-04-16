package jagongadpro.penjualanpembelian.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(GameController.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateGame() throws Exception {
        // Perform GET request to /create endpoint
        mockMvc.perform(post("/api/games"))
                .andExpect(status().isOk()) // Verify HTTP status is OK
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    assertEquals(response, "Hello World");
                });

    }
}
