package jagongadpro.penjualanpembelian.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestTemplateServiceTest {

    @Value("${app.cart}")
    String cart;

    @Value("${app.auth}")
    String auth;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestTemplateService restTemplateService;


    @Test
    void testDeleteKeranjang() throws Exception {
        // Define the URL and expected response entity
        String token = "Bearer token";
        String email = "test@example.com";
        String url = cart+"/api/cart/clear/" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();

        // Stubbing restTemplate.exchange with exact arguments
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.DELETE),
                eq(entity),
                eq(Void.class)
        )).thenReturn(responseEntity);

        // Call the method under test
        CompletableFuture<Void> futureResponse = restTemplateService.deleteKeranjang(token, email);

        // Wait for the future to complete
        Void response = futureResponse.get();

        // Assertions
        assertNull(response);

        // Verify that the exchange method was called with the correct arguments
        verify(restTemplate).exchange(
                eq(url),
                eq(HttpMethod.DELETE),
                eq(entity),
                eq(Void.class)
        );
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

        when(restTemplate.exchange(eq(auth+"/user/reduceBalance"), eq(HttpMethod.PATCH), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        CompletableFuture<WebResponse<String>> result = restTemplateService.reduceSaldo(token, user, keranjang);

        assertNotNull(result);
        assertEquals("Success", result.join().getData());
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), any(ParameterizedTypeReference.class));
    }
}
