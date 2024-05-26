package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameForTransaksiTest {
    GameForTransaksi gameForTransaksi = GameForTransaksi.builder().build();

    @Test
    void getterSetterId() {
        gameForTransaksi.setId("abc");
        assertEquals("abc", gameForTransaksi.getId());
    }

    @Test
    void getterSetterNama() {
        gameForTransaksi.setNama("Game3");
        assertEquals("Game3", gameForTransaksi.getNama());
    }

    @Test
    void getterSetterDeskripsi() {
        gameForTransaksi.setDeskripsi("baik");
        assertEquals("baik", gameForTransaksi.getDeskripsi());
    }

    @Test
    void getterSetterHarga() {
        gameForTransaksi.setHarga(10000);
        assertEquals(10000, gameForTransaksi.getHarga());
    }

    @Test
    void getterSetterKategori() {
        gameForTransaksi.setKategori("action");
        assertEquals("action", gameForTransaksi.getKategori());
    }

    @Test
    void testToString(){
        assertNotNull(gameForTransaksi.builder().toString());
    }
    

    @Test
    void builder() {
        gameForTransaksi = gameForTransaksi.builder().nama("Game1").build();
        assertEquals("Game1", gameForTransaksi.getNama());

    }

}