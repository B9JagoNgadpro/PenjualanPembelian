package jagongadpro.penjualanpembelian.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
@AutoConfigureMockMvc
@SpringBootTest
public class TransaksiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTransaksi() throws Exception {
        mockMvc.perform(post("/api/transaksi"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));

    }
}
