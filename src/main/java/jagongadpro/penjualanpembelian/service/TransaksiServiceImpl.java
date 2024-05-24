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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class TransaksiServiceImpl implements  TransaksiService{
    @Autowired
    TransaksiRepository transaksiRepository;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RestTemplateService restTemplateService;
    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;
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

        CompletableFuture<Void> deleteKeranjang = restTemplateService.deleteKeranjang(token,email);
        CompletableFuture<WebResponse<String>> reduceSaldo = restTemplateService.reduceSaldo(token,user,keranjang);
        CompletableFuture.allOf(deleteKeranjang, reduceSaldo).join();

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


    @Transactional(readOnly = true)
    public List<RiwayatTransaksiResponse> getTransaksiByEmail(String email) {
        List<Transaksi> transaksi = transaksiRepository.findAllByEmailPembeli(email);
        return transaksi.stream().map(this::toRiwayatTransaksiResponse).toList();

    }

    public RiwayatTransaksiResponse toRiwayatTransaksiResponse(Transaksi transaksi){
        Map<String, Integer> listGames= transaksi.getGames();
        ArrayList<GameTransaksiResponse> listGameTransaksiResponse = new ArrayList<>();
        for (String key : listGames.keySet()) {
            GameTransaksiResponse gameTransaksiResponse = gameService.countGamePrice(key, listGames.get(key));
            listGameTransaksiResponse.add(gameTransaksiResponse);
        }
        return RiwayatTransaksiResponse.builder()
                .tanggal(transaksi.getTanggal())
                .listGames(listGameTransaksiResponse)
                .emailPembeli(transaksi.getEmailPembeli())
                .id(transaksi.getId())
                .totalPrice(transaksi.getTotalPrice())
                .build();
    }
}
