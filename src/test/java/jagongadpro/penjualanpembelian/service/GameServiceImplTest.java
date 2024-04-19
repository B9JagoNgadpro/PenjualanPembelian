package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    @InjectMocks
    GameServiceImpl gameService;
    @Mock
    GameRepository gameRepository;

    @Mock
    ValidationService validationService;

    CreateGameRequest request;

    @Test
    void createSuccess(){
        request = new CreateGameRequest();
        request.setNama("Game1");
        request.setDeskripsi("deskripsi");
        request.setStok(100);
        request.setHarga(99);

        gameService.create(request);
        verify(validationService,times(1)).validate(any(CreateGameRequest.class));
        verify(gameRepository,times(1)).save(any(Game.class));

    }

    @Test
    void createFailed(){
        request = new CreateGameRequest();


        Set<ConstraintViolation<Object>> violations = new HashSet<>();
        doThrow(new ConstraintViolationException(violations)).when(validationService).validate(request);

        assertThrows(ConstraintViolationException.class, () ->gameService.create(request));

        verify(gameRepository,times(0)).save(any(Game.class));
    }

}