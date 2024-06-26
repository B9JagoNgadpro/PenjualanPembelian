package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.*;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.model.Transaksi;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jagongadpro.penjualanpembelian.repository.TransaksiRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.VoidAnswer1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransaksiServiceImplTest {
    @InjectMocks
    TransaksiServiceImpl transaksiService;
    @Mock
    RestTemplate restTemplate;
    @Mock
    TransaksiRepository transaksiRepository;

    @Mock
    GameRepository gameRepository;
    @Mock
    GameService gameService;

    @Mock
    RestTemplateService restTemplateService;

    @Value("${app.auth}")
    String auth;

    @Test
    public void testCreateTransaksiSuccess() {
        // Mocking RestTemplate response for user balance
        UserRequestDto userRequestDto = UserRequestDto.builder().build();
        userRequestDto.setSaldo(1000);
        WebResponse<UserRequestDto> webResponse = new WebResponse<>();
        webResponse.setData(userRequestDto);
        ResponseEntity<WebResponse<UserRequestDto>> userResponseEntity = ResponseEntity.ok(webResponse);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<WebResponse<UserRequestDto>>() {})
        )).thenReturn(userResponseEntity);

        // Mocking GameRepository responses

        // Mocking RestTemplateService async calls
        when(restTemplateService.createTransaksi(any(CreateTransaksiResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(null));
        when(restTemplateService.deleteKeranjang(anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(null));
        when(restTemplateService.reduceSaldo(anyString(), any(UserRequestDto.class), any(KeranjangDto.class)))
                .thenReturn(CompletableFuture.completedFuture(new WebResponse<>()));

        // Create a sample KeranjangDto object
        KeranjangDto keranjangDto = new KeranjangDto();
        keranjangDto.setEmail("user@example.com");
        keranjangDto.setTotalPrice(500);
        Map<String, Integer> items = new HashMap<>();
        keranjangDto.setItems(items);

        // Call the method
        transaksiService.createTransaksi(keranjangDto, "user@example.com", "Bearer token");

        // Verify interactions
        verify(restTemplate).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<WebResponse<UserRequestDto>>() {})
        );

        verify(restTemplateService).createTransaksi(any(CreateTransaksiResponse.class));
        verify(restTemplateService).deleteKeranjang(anyString(), anyString());
        verify(restTemplateService).reduceSaldo(anyString(), any(UserRequestDto.class), any(KeranjangDto.class));
    }

    @Test
    void createTransaksiFailedBalanceNotEnough(){
        Map<String,Integer> items = new HashMap<>();
        items.put("id", 2);

        String email = "example@gmail.com";
        String token = "token";

        KeranjangDto keranjangDto = new KeranjangDto();
        keranjangDto.setItems(items);
        keranjangDto.setEmail(email);
        keranjangDto.setTotalPrice(200000);

        UserRequestDto userRequestDto = UserRequestDto.builder().email("example@gmail.com").saldo(100000).build();
        WebResponse<UserRequestDto> responseUser = WebResponse.<UserRequestDto>builder().data(userRequestDto).build();
        ParameterizedTypeReference<WebResponse<UserRequestDto>> responseType = new ParameterizedTypeReference<WebResponse<UserRequestDto>>() {};
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(responseType)))
                .thenReturn(ResponseEntity.ok().body(responseUser));
        assertThrows(ResponseStatusException.class,()-> transaksiService.createTransaksi(keranjangDto, email, token) );
        verify(transaksiRepository, times(0)).save(any(Transaksi.class));
    }

    @Test
    void createTransaksiFailedGameNotFound(){
        Map<String,Integer> items = new HashMap<>();
        items.put("id", 2);

        String email = "example@gmail.com";
        String token = "token";

        KeranjangDto keranjangDto = new KeranjangDto();
        keranjangDto.setItems(items);
        keranjangDto.setEmail(email);
        keranjangDto.setTotalPrice(20000);

        UserRequestDto userRequestDto = UserRequestDto.builder().email("example@gmail.com").saldo(100000).build();
        WebResponse<UserRequestDto> responseUser = WebResponse.<UserRequestDto>builder().data(userRequestDto).build();
        ParameterizedTypeReference<WebResponse<UserRequestDto>> responseType = new ParameterizedTypeReference<WebResponse<UserRequestDto>>() {};
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(responseType)))
                .thenReturn(ResponseEntity.ok().body(responseUser));
        when(gameRepository.findById(any(String.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,()-> transaksiService.createTransaksi(keranjangDto, email, token) );
        verify(transaksiRepository, times(0)).save(any(Transaksi.class));
    }

    @Test
    void createTransaksiFailedStockNotEnough(){
        Map<String,Integer> items = new HashMap<>();
        items.put("id", 2);

        String email = "example@gmail.com";
        String token = "token";

        KeranjangDto keranjangDto = new KeranjangDto();
        keranjangDto.setItems(items);
        keranjangDto.setEmail(email);
        keranjangDto.setTotalPrice(10000);

        UserRequestDto userRequestDto = UserRequestDto.builder().email("example@gmail.com").saldo(100000).build();
        WebResponse<UserRequestDto> responseUser = WebResponse.<UserRequestDto>builder().data(userRequestDto).build();
        ParameterizedTypeReference<WebResponse<UserRequestDto>> responseType = new ParameterizedTypeReference<WebResponse<UserRequestDto>>() {};
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(responseType)))
                .thenReturn(ResponseEntity.ok().body(responseUser));
        Game game = new Game.GameBuilder().stok(1).build();
        when(gameRepository.findById("id")).thenReturn(Optional.of(game));
        assertEquals(game.getStok(),1);
        assertThrows(ResponseStatusException.class, ()-> transaksiService.createTransaksi(keranjangDto, email, token));
    }
    @Test
    void testGetTransaksiByEmailMethod(){
        String email = "email";

        Map<String, Integer> games = new HashMap<>();
        games.put("id", 2);
        List<Transaksi> listTransaksi = new ArrayList<>();
        listTransaksi.add(Transaksi.builder().emailPembeli(email).games(games).totalPrice(10000).id("id-1").build());
        when(transaksiRepository.findAllByEmailPembeli(email)).thenReturn(listTransaksi);

        GameTransaksiResponse gameTransaksiResponse = new GameTransaksiResponse();
        gameTransaksiResponse.setNama("nama");
        gameTransaksiResponse.setHargaSatuan(2000);
        when(gameService.countGamePrice("id",2)).thenReturn(gameTransaksiResponse);

        List<RiwayatTransaksiResponse> riwayat = transaksiService.getTransaksiByEmail(email);
        assertNotNull(riwayat);
        assertEquals(riwayat.size(),1);
        assertEquals(riwayat.get(0).getEmailPembeli(), email);
    }


}