package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.model.Game;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ValidationServiceTest {
    @Autowired
    ValidationService validationService;
    CreateGameRequest request;
    @Test
    void testValidationWorks(){
        request = new CreateGameRequest();

        Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
            validationService.validate(request);
        });

    }

}