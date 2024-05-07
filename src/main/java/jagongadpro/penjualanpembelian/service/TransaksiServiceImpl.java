package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.*;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.model.Transaksi;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jagongadpro.penjualanpembelian.repository.TransaksiRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransaksiServiceImpl implements  TransaksiService{
    @Autowired
    TransaksiRepository transaksiRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GameRepository gameRepository;
    @Transactional
    public TransaksiResponse createTransaksi(KeranjangDto keranjang, String email, String token){
        //tembah auth buat dptin saldonya
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ParameterizedTypeReference<WebResponse<UserRequestDto>> responseType = new ParameterizedTypeReference<WebResponse<UserRequestDto>>() {};
        ResponseEntity<WebResponse<UserRequestDto>> userResponse = restTemplate.exchange("http://localhost:8080/user/me", HttpMethod.GET, entity, responseType);
        UserRequestDto user = userResponse.getBody().getData();

        //cek saldo kalo kureng throw except
        if (user.getSaldo()< keranjang.getTotalPrice()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo anda tidak cukup");
        }

        //kurangin stok semua game
        Map<String, Integer> listGames = keranjang.getItems();
        for (String key : listGames.keySet()) {
            Game game = gameRepository.findById(key).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Game tidak ditemukan"));
            Integer stockSekarang = game.getStok();
            if (stockSekarang- listGames.get(key)<0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock Game Kurang");
            }
            game.setStok(stockSekarang- listGames.get(key));
            gameRepository.save(game);
        }


        //apus isi keranjang
        headers = new HttpHeaders();
        headers.add("Authorization", token);
        entity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> deleteKeranjangResponse= restTemplate.exchange("http://localhost:8081/api/cart/clear/"+email, HttpMethod.DELETE,entity, Void.class);

        //kurangin saldo
        headers = new HttpHeaders();
        headers.add("Authorization", token);
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("saldo", user.getSaldo()- keranjang.getTotalPrice());
        HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<WebResponse<String>> responseTypeBalance = new ParameterizedTypeReference<WebResponse<String>>() {};
        ResponseEntity<WebResponse<String>> updateBalance= restTemplate.exchange("http://localhost:8080/user/reduceBalance", HttpMethod.PATCH,requestEntity,responseTypeBalance );

        //bikin transaksi

        Transaksi transaksi = Transaksi.builder().emailPembeli(keranjang.getEmail()).games(keranjang.getItems()).emailPembeli(email).totalPrice(keranjang.getTotalPrice()).build();
        transaksiRepository.save(transaksi);

        return TransaksiResponse.builder()
                .id(transaksi.getId())
                .emailPembeli(transaksi.getEmailPembeli())
                .games(transaksi.getGames())
                .tanggal(transaksi.getTanggal())
                .totalPrice(transaksi.getTotalPrice())
                .build();

    }
}