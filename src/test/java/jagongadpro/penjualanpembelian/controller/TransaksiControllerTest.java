package jagongadpro.penjualanpembelian.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jagongadpro.penjualanpembelian.dto.*;
import jagongadpro.penjualanpembelian.service.GameService;
import jagongadpro.penjualanpembelian.service.TransaksiService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @MockBean
    TransaksiService transaksiService;

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
    public void GetTransaksiGameNotFound() throws Exception {
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
    @Test
    void createTransaksiSuccess() throws  Exception{
        String token ="token";
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
        when(transaksiService.createTransaksi(keranjangResponse, "example@gmail.com", token)).thenReturn(TransaksiResponse.builder().games(keranjangResponse.getItems()).emailPembeli(email).totalPrice(keranjangResponse.getTotalPrice()).build());
        mockMvc.perform(post("/api/transaksi/"+email).header("Authorization",token).contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isCreated())
                .andDo(result -> {
                   WebResponse<TransaksiResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<TransaksiResponse>>() {
                   }) ;
                   assertNotNull(response.getData());
                   assertNull(response.getErrors());
                   assertEquals(response.getData().getEmailPembeli(), "example@gmail.com");
                });
    }

    @Test
    void createTransaksiFailedKeranjangNotFound() throws  Exception{
        String token ="token";
        String email = "example@gmail.com";
        Map<String, Integer> items = new HashMap<>();
        items.put("idGames", 9);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(KeranjangDto.class)))
                .thenReturn(ResponseEntity.notFound().build());
         mockMvc.perform(post("/api/transaksi/"+email).header("Authorization",token).contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().isNotFound())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    }) ;
                    assertNull(response.getData());
                    assertNotNull(response.getErrors());
                    assertEquals(response.getErrors(), "Keranjang tidak ditemukan");
                });
    }
    @Test
    void createTransaksiFailedNotAuthenticated() throws  Exception {
        mockMvc.perform(post("/api/transaksi/email").contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().is4xxClientError());
    }

    @Test
    void getRiwayatTransaksiByEmail() throws  Exception{
        String email = "email";
        String token = "token";
        ArrayList<RiwayatTransaksiResponse> riwayat = new ArrayList<>();
        riwayat.add(RiwayatTransaksiResponse.builder().emailPembeli(email).build());
        when(transaksiService.getTransaksiByEmail(email)).thenReturn(riwayat);
        mockMvc.perform(get("/api/transaksi/"+email).header("Authorization", token)).andExpectAll(status().isOk())
                .andDo(result -> {
                   WebResponse<List<RiwayatTransaksiResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<List<RiwayatTransaksiResponse>>>() {
                   });

                   assertNotNull(response.getData());
                   assertNull(response.getErrors());
                   assertEquals(response.getData().get(0).getEmailPembeli(), email);
                });
    }
}