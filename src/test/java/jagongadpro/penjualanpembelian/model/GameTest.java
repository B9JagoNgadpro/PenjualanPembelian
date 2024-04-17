package jagongadpro.penjualanpembelian.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class GameTest {
    Game game = new Game();

    @Test
    void testGetterSetterNama(){
        game.setNama("Game1");
        assertEquals("Game1", game.getNama());
    }

    @Test
    void testGetterSetterId(){
        game.setId("abc");
        assertEquals("abc", game.getId());
    }
    @Test
    void testGetterSetterStok(){
        game.setStok(10);
        assertEquals(10, game.getStok());
    }

    @Test
    void testGetterSetterHarga(){
        game.setHarga(1000);
        assertEquals(1000, game.getHarga());
    }

    @Test
    void testGetterSetterDeskripsi(){
        game.setDeskripsi("bagus");
        assertEquals("bagus", game.getDeskripsi());
    }
    @Test
    void testGetterSetterKategori(){
        game.setKategori("action");
        assertEquals("action", game.getKategori());
    }


}