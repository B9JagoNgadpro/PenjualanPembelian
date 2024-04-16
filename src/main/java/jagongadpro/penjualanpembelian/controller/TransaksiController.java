package jagongadpro.penjualanpembelian.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransaksiController {
    @PostMapping(value = "/api/transaksi")
    public ResponseEntity<String> createProductPost(){
        return ResponseEntity.ok().body("Hello World");
    }
}
