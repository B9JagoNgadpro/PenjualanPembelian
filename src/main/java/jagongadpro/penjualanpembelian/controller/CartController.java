package jagongadpro.penjualanpembelian.controller;

import jagongadpro.penjualanpembelian.config.CartPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartPublisher cartPublisher;

    @GetMapping("/api/cart/view")
    public String viewCart(@RequestParam String email) {
        cartPublisher.sendViewCartRequest(email);
        return cartPublisher.getCartResponse();
    }
}
