package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameResponseTest {
    GameResponse gameResponse = GameResponse.builder().build();

    @Test
    void getterSetterId() {
        gameResponse.setId("abc");
        assertEquals("abc", gameResponse.getId());
    }

    @Test
    void getterSetterNama() {
        gameResponse.setNama("Game3");
        assertEquals("Game3", gameResponse.getNama());
    }

    @Test
    void getterSetterDeskripsi() {
        gameResponse.setDeskripsi("baik");
        assertEquals("baik", gameResponse.getDeskripsi());
    }

    @Test
    void getterSetterHarga() {
        gameResponse.setHarga(10000);
        assertEquals(10000, gameResponse.getHarga());
    }

    @Test
    void getterSetterKategori() {
        gameResponse.setKategori("action");
        assertEquals("action", gameResponse.getKategori());
    }

    @Test
    void testToString(){
        assertNotNull(GameResponse.builder().toString());
    }

    @Test
    void getterSetterStok() {
        gameResponse.setStok(100);
        assertEquals(100, gameResponse.getStok());
    }

    @Test
    void builder() {
        gameResponse = GameResponse.builder().nama("Game1").build();
        assertEquals("Game1", gameResponse.getNama());

    }
}