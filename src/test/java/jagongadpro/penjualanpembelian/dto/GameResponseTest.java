package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameResponseTest {
    GameResponse gameResponse = new GameResponse();

    @Test
    void getterSetterId() {
        gameResponse.setId("abc");
        assertEquals(gameResponse.getId(),"abc");
    }

    @Test
    void getterSetterNama() {
        gameResponse.setNama("Game3");
        assertEquals(gameResponse.getNama(),"Game3");
    }

    @Test
    void getterSetterDeskripsi() {
        gameResponse.setDeskripsi("baik");
        assertEquals(gameResponse.getDeskripsi(),"baik");
    }

    @Test
    void getterSetterHarga() {
        gameResponse.setHarga(10000);
        assertEquals(gameResponse.getHarga(),10000);
    }

    @Test
    void getterSetterKategori() {
        gameResponse.setKategori("action");
        assertEquals(gameResponse.getKategori(),"action");
    }

    @Test
    void testToString(){
        assertNotNull(GameResponse.builder().toString());
    }

    @Test
    void getterSetterStok() {
        gameResponse.setStok(100);
        assertEquals(gameResponse.getStok(),100);
    }

    @Test
    void builder() {
        gameResponse = GameResponse.builder().nama("Game1").build();
        assertEquals("Game1", gameResponse.getNama());

    }
}