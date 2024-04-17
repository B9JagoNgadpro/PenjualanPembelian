package jagongadpro.penjualanpembelian.dto;

import jagongadpro.penjualanpembelian.model.Game;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateGameRequestTest {
    @Autowired
    private Validator validator;
    public void validate(Object test) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(test);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
    @Test
    void createGameRequestSuccess(){
        CreateGameRequest game = new CreateGameRequest();
        game.setNama("Game1");
        game.setDeskripsi("deskripsi");
        game.setStok(10);
        game.setHarga(10000);
        validate(game);

    }

    @Test
    void createBlankNameGameRequest(){
        CreateGameRequest game = new CreateGameRequest();
        game.setDeskripsi("deskripsi");
        game.setStok(10);
        game.setHarga(10000);

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(game);
        });

        assertEquals("nama: must not be blank", exception.getMessage());

    }

    @Test
    void createHargaNotValid(){
        CreateGameRequest game = new CreateGameRequest();
        game.setNama("Game1");
        game.setDeskripsi("deskripsi");
        game.setStok(100);
        game.setHarga(0);

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(game);
        });

        assertEquals("harga: must be greater than 0", exception.getMessage());

    }

    @Test
    void createStokNotValid(){
        CreateGameRequest game = new CreateGameRequest();
        game.setNama("Game1");
        game.setDeskripsi("deskripsi");
        game.setStok(-22);
        game.setHarga(10000);

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(game);
        });

        assertEquals("stok: must be greater than or equal to 0", exception.getMessage());

    }
}