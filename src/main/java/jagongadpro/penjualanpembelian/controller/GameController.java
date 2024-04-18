package jagongadpro.penjualanpembelian.controller;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
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



@RestController
public class GameController {
    @Autowired
    GameService gameService;

    @PostMapping(value = "/api/games", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> createProductPost(@RequestBody CreateGameRequest request){
        gameService.create(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(value = "/api/games/all")
    public ResponseEntity<String> GetAllProducts(){
        return ResponseEntity.ok().body("Hello World");
    }
    @GetMapping(value = "/api/games")
    public ResponseEntity<String> FilterGames(){
        return ResponseEntity.ok().body("Hello World");
    }

}
