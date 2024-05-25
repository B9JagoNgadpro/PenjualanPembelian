package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.KeranjangDto;
import jagongadpro.penjualanpembelian.dto.UserRequestDto;
import jagongadpro.penjualanpembelian.dto.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class RestTemplateService {
    @Value("${app.cart}")
    String cart;

    @Value("${app.auth}")
    String auth;
    @Autowired
    RestTemplate restTemplate;
    @Async
    public CompletableFuture<Void> deleteKeranjang(String token, String email){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> deleteKeranjangResponse= restTemplate.exchange(cart+"api/cart/clear/"+email, HttpMethod.DELETE,entity, Void.class);
        Void response = deleteKeranjangResponse.getBody();
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<WebResponse<String>> reduceSaldo(String token, UserRequestDto user, KeranjangDto keranjang){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("saldo", user.getSaldo()- keranjang.getTotalPrice());
        HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<WebResponse<String>> responseTypeBalance = new ParameterizedTypeReference<WebResponse<String>>() {};
        ResponseEntity<WebResponse<String>> updateBalance= restTemplate.exchange(auth+"user/reduceBalance", HttpMethod.PATCH,requestEntity,responseTypeBalance );
        WebResponse<String> newBalance = updateBalance.getBody();
        return CompletableFuture.completedFuture(newBalance);
    }
}
