package jagongadpro.penjualanpembelian.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jagongadpro.penjualanpembelian.dto.*;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.model.Transaksi;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jagongadpro.penjualanpembelian.repository.TransaksiRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    ObjectMapper objectMapper;

    @Autowired
    GameService gameService;

    @Value("${app.auth}")
    String auth;

    @Transactional
    public void createTransaksi(KeranjangDto keranjang, String email, String token) {
        //tembah auth buat dptin saldonya
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ParameterizedTypeReference<WebResponse<UserRequestDto>> responseType = new ParameterizedTypeReference<WebResponse<UserRequestDto>>() {};
        ResponseEntity<WebResponse<UserRequestDto>> userResponse = restTemplate.exchange(auth+"user/me", HttpMethod.GET, entity, responseType);
        UserRequestDto user = userResponse.getBody().getData();

        //cek saldo kalo kureng throw except
        if (user.getSaldo()< keranjang.getTotalPrice()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo anda tidak cukup");
        }

        List<GameForTransaksi> games = new ArrayList<>();
        //kurangin stok semua game
        Map<String, Integer> listGames = keranjang.getItems();
        for (Map.Entry<String, Integer> entry : listGames.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            Game game = gameRepository.findById(key).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Game tidak ditemukan"));

            Integer stockSekarang = game.getStok();
            if (stockSekarang - value < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock Game Kurang");
            }

            game.setStok(stockSekarang - value);
            gameRepository.save(game);
            GameForTransaksi gameForTransaksi = GameForTransaksi.builder()
                                                .nama(game.getNama())
                                                .id(game.getId())
                                                .deskripsi(game.getDeskripsi())
                                                .harga(game.getHarga())
                                                .kategori(game.getKategori())
                                                .penjual_id(game.getIdPenjual()).build();
            games.add(gameForTransaksi);

        }
        Date date = new Date();

        // Convert java.util.Date to Instant
        Instant instant = date.toInstant();

        // Convert Instant to ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));

        // Format ZonedDateTime to RFC 3339
        String rfc3339String = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        String id =  UUID.randomUUID().toString();
        CreateTransaksiResponse createTransaksiResponse = CreateTransaksiResponse.builder()
                .id(id)
                .games(games)
                .total_harga(keranjang.getTotalPrice())
                .tanggal_pembayaran(rfc3339String)
                .pembeli_id(keranjang.getEmail())
                .build();
        CompletableFuture<HashMap<String, String>> createTransaksi = restTemplateService.createTransaksi(createTransaksiResponse);
        CompletableFuture<Void> deleteKeranjang = restTemplateService.deleteKeranjang(token,email);
        CompletableFuture<WebResponse<String>> reduceSaldo = restTemplateService.reduceSaldo(token,user,keranjang);
        CompletableFuture.allOf(deleteKeranjang,createTransaksi ,reduceSaldo).join();
    }


    @Transactional(readOnly = true)
    public List<RiwayatTransaksiResponse> getTransaksiByEmail(String email) {
        List<Transaksi> transaksi = transaksiRepository.findAllByEmailPembeli(email);
        return transaksi.stream().map(this::toRiwayatTransaksiResponse).toList();

    }

    public RiwayatTransaksiResponse toRiwayatTransaksiResponse(Transaksi transaksi){
        Map<String, Integer> listGames= transaksi.getGames();
        ArrayList<GameTransaksiResponse> listGameTransaksiResponse = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : listGames.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            GameTransaksiResponse gameTransaksiResponse = gameService.countGamePrice(key, value);
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
