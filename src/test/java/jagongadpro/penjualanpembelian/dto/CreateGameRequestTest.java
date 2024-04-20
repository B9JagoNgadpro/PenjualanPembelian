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
        CreateGameRequest request = CreateGameRequest.builder().nama("Game1").deskripsi("deskripsi").stok(10).harga(10000).kategori("action").build();
        validate(request);
        assertEquals(request.getNama(), "Game1");
        assertEquals(request.getDeskripsi(), "deskripsi");
        assertEquals(10, request.getStok());
        assertEquals(10000, request.getHarga());
        assertEquals("action", request.getKategori());

    }

    @Test
    void createBlankNameGameRequest(){
        CreateGameRequest request = new CreateGameRequest();
        request.setDeskripsi("deskripsi");
        request.setStok(10);
        request.setHarga(10000);
        request.setNama("");

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(request);
        });

        assertEquals("nama: must not be blank", exception.getMessage());

    }

    @Test
    void createHargaNotValid(){
        CreateGameRequest request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setStok(100);
        request.setHarga(0);

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(request);
        });

        assertEquals("harga: must be greater than 0", exception.getMessage());

    }
    @Test
    void createHargaBlank(){
        CreateGameRequest request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setStok(100);



        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(request);
        });

        assertEquals("harga: must not be null", exception.getMessage());

    }

    @Test
    void createStokNotValid(){
        CreateGameRequest request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setStok(-22);
        request.setHarga(10000);

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(request);
        });

        assertEquals("stok: must be greater than or equal to 0", exception.getMessage());

    }
    @Test
    void createStokBlank(){
        CreateGameRequest request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setHarga(10000);

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validate(request);
        });

        assertEquals("stok: must not be null", exception.getMessage());

    }
}