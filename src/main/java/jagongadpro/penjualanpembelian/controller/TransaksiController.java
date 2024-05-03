package jagongadpro.penjualanpembelian.controller;

import jagongadpro.penjualanpembelian.dto.GameTransaksiResponse;
import jagongadpro.penjualanpembelian.dto.KeranjangDto;
import jagongadpro.penjualanpembelian.dto.ListGameResponse;
import jagongadpro.penjualanpembelian.dto.WebResponse;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jagongadpro.penjualanpembelian.service.GameService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class TransaksiController {
    @Autowired
    GameService gameService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/api/transaksi/display/{email}")
    public WebResponse<ListGameResponse> createProductPost(@PathVariable String email){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<KeranjangDto> response= restTemplate.exchange("http://localhost:8081/api/cart/view/"+email, HttpMethod.GET,entity ,KeranjangDto.class);
        if (response.getStatusCode() ==  HttpStatus.NOT_FOUND){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan");
        }
        KeranjangDto keranjang = response.getBody();

        ArrayList<GameTransaksiResponse> listGame = new ArrayList<>();
        Map<String, Integer> listGames = keranjang.getItems();
        for (String key : listGames.keySet()) {
            GameTransaksiResponse gameTransaksiResponse = gameService.countGamePrice(key, listGames.get(key));
            listGame.add(gameTransaksiResponse);
        }
        ListGameResponse listGameResponse = ListGameResponse.builder().listGames(listGame).totalPrice(keranjang.getTotalPrice()).build();
        return WebResponse.<ListGameResponse>builder().data(listGameResponse).build();
    }


}
