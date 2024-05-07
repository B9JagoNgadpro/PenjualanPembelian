package jagongadpro.penjualanpembelian.model;

import jagongadpro.penjualanpembelian.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    void MakeGameBuilder(){
        Game game1 = new Game.GameBuilder().nama("Game1").stok(10).harga(1000).kategori("action").deskripsi("bagus").build();
        assertEquals("Game1", game1.getNama());
        assertEquals(1000, game1.getHarga());
        assertEquals(10, game1.getStok());
        assertEquals("action", game1.getKategori());
        assertEquals("bagus", game1.getDeskripsi());
    }
}
