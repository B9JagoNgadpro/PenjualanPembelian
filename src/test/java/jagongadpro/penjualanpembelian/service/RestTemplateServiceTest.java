package jagongadpro.penjualanpembelian.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


import jagongadpro.penjualanpembelian.dto.KeranjangDto;
import jagongadpro.penjualanpembelian.dto.UserRequestDto;
import jagongadpro.penjualanpembelian.dto.WebResponse;

import org.junit.jupiter.api.Test;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestTemplateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestTemplateService restTemplateService;


    @Test
    void testDeleteKeranjang() {
        String token = "Bearer token";
        String email = "test@example.com";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(null);
        when(restTemplate.exchange(eq("http://localhost:8081/api/cart/clear/" + email), eq(HttpMethod.DELETE), eq(entity), eq(Void.class)))
                .thenReturn(responseEntity);

        CompletableFuture<Void> result = restTemplateService.deleteKeranjang(token, email);

        assertNotNull(result);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class));
    }

    @Test
    void testReduceSaldo() {
        String token = "Bearer token";
        UserRequestDto user = UserRequestDto.builder().build();
        user.setSaldo(1000);
        KeranjangDto keranjang = new KeranjangDto();
        keranjang.setTotalPrice(200);

        HttpHeaders headers = new HttpHeaders();

        WebResponse<String> webResponse = new WebResponse<>();
        webResponse.setData("Success");
        ResponseEntity<WebResponse<String>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(webResponse);

        when(restTemplate.exchange(eq("http://localhost:8080/user/reduceBalance"), eq(HttpMethod.PATCH), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        CompletableFuture<WebResponse<String>> result = restTemplateService.reduceSaldo(token, user, keranjang);

        assertNotNull(result);
        assertEquals("Success", result.join().getData());
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), any(ParameterizedTypeReference.class));
    }
}
