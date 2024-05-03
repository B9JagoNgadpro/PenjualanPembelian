package jagongadpro.penjualanpembelian.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jagongadpro.penjualanpembelian.dto.*;
import jagongadpro.penjualanpembelian.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
@AutoConfigureMockMvc
@WebMvcTest(TransaksiController.class)
@ExtendWith(MockitoExtension.class)
public class TransaksiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GameService gameService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RestTemplate restTemplate;

    @Test
    public void GetTransaksi() throws Exception {
        String email = "example@gmail.com";
        Map<String, Integer> items = new HashMap<>();
        items.put("idGames", 9);
        KeranjangDto keranjangResponse = new KeranjangDto();
        keranjangResponse.setEmail(email);
        keranjangResponse.setItems(items);
        keranjangResponse.setTotalPrice(90000);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(KeranjangDto.class)))
                .thenReturn(ResponseEntity.ok(keranjangResponse));
        GameTransaksiResponse gameTransaksiResponse = new GameTransaksiResponse();
        gameTransaksiResponse.setNama("Example");
        gameTransaksiResponse.setQuantity(9);
        gameTransaksiResponse.setHargaSatuan(10000);
        gameTransaksiResponse.setTotal(90000);

        when(gameService.countGamePrice(eq("idGames"), anyInt())).thenReturn(gameTransaksiResponse);
        mockMvc.perform(get("/api/transaksi/display/" + email).contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<ListGameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getData());
                    assertNull(response.getErrors());
                    GameTransaksiResponse gameResponse = response.getData().getListGames().get(0);
                    assertEquals(gameResponse.getNama(), "Example");
                    assertEquals(gameResponse.getQuantity(), 9);
                    assertEquals(gameResponse.getTotal(), 90000);
                });


    }

    @Test
    public void GetTransaksiEmailNotFound() throws Exception {
        String email = "example@gmail.com";
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(KeranjangDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        mockMvc.perform(get("/api/transaksi/display/" + email).contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isNotFound())
                .andDo(result -> {
                    WebResponse<ListGameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getData());
                    assertNotNull(response.getErrors());
                    assertEquals(response.getErrors(), "Keranjang tidak ditemukan");
                });


    }

    @Test
    public void GetTransaksiGamelNotFound() throws Exception {
        String email = "example@gmail.com";
        Map<String, Integer> items = new HashMap<>();
        items.put("idGames", 9);
        KeranjangDto keranjangResponse = new KeranjangDto();
        keranjangResponse.setEmail(email);
        keranjangResponse.setItems(items);
        keranjangResponse.setTotalPrice(90000);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(KeranjangDto.class)))
                .thenReturn(ResponseEntity.ok(keranjangResponse));


        when(gameService.countGamePrice(eq("idGames"), anyInt())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Game tidak ditemukan"));
        mockMvc.perform(get("/api/transaksi/display/" + email).contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isNotFound())
                .andDo(result -> {
                    WebResponse<ListGameResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getData());
                    assertNotNull(response.getErrors());
                    assertEquals(response.getErrors(), "Game tidak ditemukan");

                });

    }
}
