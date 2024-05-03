package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilterGameRequestTest {
    FilterGameRequest request = new FilterGameRequest();
    @Test
    void getterSetterNama() {
        request.setNama("Game1");
        assertEquals("Game1", request.getNama());
    }

    @Test
    void getterSetterHarga() {
        request.setHarga(10000);
        assertEquals(10000, request.getHarga());
    }
    @Test
    void getterSetterKategori() {
        request.setKategori("action");
        assertEquals("action", request.getKategori());
    }

    @Test
    void builder() {
        request = FilterGameRequest.builder().nama("Game2").kategori("romance").harga(2000).build();
        assertEquals("romance", request.getKategori());
        assertEquals("Game2", request.getNama());
        assertEquals(2000, request.getHarga());

    }

    @Test
    void testToString(){
        assertNotNull(FilterGameRequest.builder().toString());
    }
}
