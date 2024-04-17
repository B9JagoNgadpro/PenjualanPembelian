package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebResponseTest {
    @Test
    void testWebResponseBuilder(){
        WebResponse<String> response = WebResponse.<String>builder().data("OK").errors("error").build();
        assertEquals(response.getData(),"OK");
        assertEquals(response.getErrors(), "error");
    }

}