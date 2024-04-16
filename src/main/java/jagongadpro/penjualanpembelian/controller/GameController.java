package jagongadpro.penjualanpembelian.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class GameController {
    @PostMapping(value = "/api/games")
    public ResponseEntity<String> createProductPost(){
        System.out.println("masukkk");
        return ResponseEntity.ok().body("Hello World");
    }

    @GetMapping(value = "/api/games/all")
    public ResponseEntity<String> GetAllProducts(){
        System.out.println("masukkk");
        return ResponseEntity.ok().body("Hello World");
    }

}
