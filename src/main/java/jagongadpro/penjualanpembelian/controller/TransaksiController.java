package jagongadpro.penjualanpembelian.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jagongadpro.penjualanpembelian.dto.*;
import jagongadpro.penjualanpembelian.service.GameService;
import jagongadpro.penjualanpembelian.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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


    @Value("${app.cart}")
    String cart;


    @GetMapping(value = "/display/{email}")
    public WebResponse<ListGameResponse> createProductPost(@PathVariable String email){
        KeranjangDto keranjang = getKeranjangByEmail(email);

        ArrayList<GameTransaksiResponse> listGame = new ArrayList<>();
        Map<String, Integer> listGames = keranjang.getItems();
        for (Map.Entry<String, Integer> entry : listGames.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            GameTransaksiResponse gameTransaksiResponse = gameService.countGamePrice(key, value);
            listGame.add(gameTransaksiResponse);
        }

        ListGameResponse listGameResponse = ListGameResponse.builder().listGames(listGame).totalPrice(keranjang.getTotalPrice()).build();
        return WebResponse.<ListGameResponse>builder().data(listGameResponse).build();
    }


    @CrossOrigin
    @PostMapping("/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<String> createTransaksi(@PathVariable String email, @RequestHeader("Authorization") String token) {
        KeranjangDto keranjang = getKeranjangByEmail(email);
        transaksiService.createTransaksi(keranjang,email,token);
        return WebResponse.<String>builder().data("Ok").build();
    }

    @GetMapping("/{email}")
    public WebResponse<List<RiwayatTransaksiResponse>>  getRiwayatTransaksiByEmail(@PathVariable String email, @RequestHeader("Authorization") String token){
        List<RiwayatTransaksiResponse> riwayatTransaksi = transaksiService.getTransaksiByEmail(email);
        return WebResponse.<List<RiwayatTransaksiResponse>>builder().data(riwayatTransaksi).build();
    }

    public KeranjangDto getKeranjangByEmail(String email){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<KeranjangDto> response= restTemplate.exchange(cart+"/api/cart/view/"+email, HttpMethod.GET,entity ,KeranjangDto.class);
        if (response.getStatusCode() ==  HttpStatus.NOT_FOUND){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan");
        }
        return  response.getBody();
    }


}
