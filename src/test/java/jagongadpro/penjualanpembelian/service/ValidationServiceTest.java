package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;

import jakarta.validation.ConstraintViolationException;

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

        assertThrows(ConstraintViolationException.class, () -> {
            validationService.validate(request);
        });

    }

}