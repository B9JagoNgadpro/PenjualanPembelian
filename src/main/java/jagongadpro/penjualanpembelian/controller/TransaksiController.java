package jagongadpro.penjualanpembelian.controller;

import jagongadpro.penjualanpembelian.dto.*;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.model.Transaksi;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jagongadpro.penjualanpembelian.service.GameService;
import jagongadpro.penjualanpembelian.service.TransaksiService;
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
@RequestMapping("/api/transaksi")
public class TransaksiController {
    @Autowired
    GameService gameService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransaksiService transaksiService;

    @GetMapping(value = "/display/{email}")
    public WebResponse<ListGameResponse> createProductPost(@PathVariable String email){
        KeranjangDto keranjang = getKeranjangByEmail(email);

        ArrayList<GameTransaksiResponse> listGame = new ArrayList<>();
        Map<String, Integer> listGames = keranjang.getItems();
        for (String key : listGames.keySet()) {
            GameTransaksiResponse gameTransaksiResponse = gameService.countGamePrice(key, listGames.get(key));
            listGame.add(gameTransaksiResponse);
        }
        ListGameResponse listGameResponse = ListGameResponse.builder().listGames(listGame).totalPrice(keranjang.getTotalPrice()).build();
        return WebResponse.<ListGameResponse>builder().data(listGameResponse).build();
    }

    @PostMapping("/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<TransaksiResponse> createTransaksi(@PathVariable String email, @RequestHeader("Authorization") String token){
        KeranjangDto keranjang = getKeranjangByEmail(email);
        TransaksiResponse transaksi= transaksiService.createTransaksi(keranjang, email, token);
        return WebResponse.<TransaksiResponse>builder().data(transaksi).build();
    }

    public KeranjangDto getKeranjangByEmail(String email){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<KeranjangDto> response= restTemplate.exchange("http://localhost:8081/api/cart/view/"+email, HttpMethod.GET,entity ,KeranjangDto.class);
        if (response.getStatusCode() ==  HttpStatus.NOT_FOUND){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan");
        }
        KeranjangDto keranjang = response.getBody();
        return  keranjang;
    }


}
