package jagongadpro.penjualanpembelian.controller;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.FilterGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;
import jagongadpro.penjualanpembelian.dto.WebResponse;
import jagongadpro.penjualanpembelian.service.GameService;
import jagongadpro.penjualanpembelian.service.GameServiceImpl;
import jagongadpro.penjualanpembelian.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GameController {
    @Autowired
    GameService gameService;

    @PostMapping(value = "/api/games", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<GameResponse> createProductPost(@RequestBody CreateGameRequest request) {
        GameResponse response = gameService.create(request);
        return WebResponse.<GameResponse>builder().data(response).build();
    }

    @GetMapping(value = "/api/games/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<GameResponse>> GetAllProducts() {
        return WebResponse.<List<GameResponse>>builder().data(gameService.getAll()).build();
    }

    @GetMapping(value = "/api/games")
    public WebResponse<List<GameResponse>> FilterGame(@RequestParam(value = "nama", required = false) String nama,
                                                      @RequestParam(value = "kategori", required = false) String kategori,
                                                      @RequestParam(value = "harga", required = false) Integer harga) {
        FilterGameRequest request = FilterGameRequest.builder()
                .nama(nama)
                .harga(harga)
                .kategori(kategori)
                .build();
        return WebResponse.<List<GameResponse>>builder()
                .data(gameService.filter(request))
                .build();

    }

    @GetMapping(value = "/api/games/{id}")
    public WebResponse<GameResponse> getGameById(@PathVariable("id") String id) {
        GameResponse gameResponse = gameService.getById(id);
        return WebResponse.<GameResponse>builder().data(gameResponse).build();
    }
}