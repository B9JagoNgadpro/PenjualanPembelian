package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.FilterGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;
import jagongadpro.penjualanpembelian.dto.GameTransaksiResponse;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.web.server.ResponseStatusException;

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
    @Test
    void getAll(){
        List<GameResponse> games = gameService.getAll();
        verify(gameRepository,times(1)).findAll();

    }

    @Test
    void filter(){
        FilterGameRequest request = FilterGameRequest.builder().nama("Games").build();
        List<GameResponse> games = gameService.filter(request);
        verify(gameRepository, times(1)).findAll(any(Specification.class));

    }
    @Test
    void getByIdTestSuccess(){
        Game game1 = new Game.GameBuilder().nama("Game1").build();
        doReturn(Optional.of(game1)).when(gameRepository).findById("id");
        GameResponse response = gameService.getById("id");
        assertNotNull(response);
        assertEquals(response.getNama(), "Game1");

    }

    @Test
    void getByIdTestFailed(){

        assertThrows(ResponseStatusException.class, ()->gameService.getById("id"));

    }

    @Test
    void countGamePriceSuccess(){
        String key = "key";
        Integer quantity = 2;

        when(gameRepository.findById(key)).thenReturn(Optional.of(new Game.GameBuilder().harga(10000).build()));
        GameTransaksiResponse gameTransaksiResponse = gameService.countGamePrice(key,quantity);
        assertEquals(gameTransaksiResponse.getQuantity(),2);
        assertEquals(gameTransaksiResponse.getHargaSatuan(), 10000);
        assertEquals(gameTransaksiResponse.getTotal(), 20000);
    }

    @Test
    void countGamePriceNotFound(){
        String key = "key";
        Integer quantity = 2;

         assertThrows(ResponseStatusException.class, () -> {
            gameService.countGamePrice(key, quantity);
        });
    }

}