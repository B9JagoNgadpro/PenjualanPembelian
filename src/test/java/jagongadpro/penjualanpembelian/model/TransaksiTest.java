package jagongadpro.penjualanpembelian.model;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransaksiTest {
    Date tanggal = new Date();
    Map<String,Integer> games = new HashMap<>();
    Transaksi transaksi = Transaksi.builder().id("id").games(games).emailPembeli("email").totalPrice(50000).build();

    @Test
    void getterId(){
        assertEquals(transaksi.getId(), "id");
    }

    @Test
    void getterGames(){
        assertEquals(transaksi.getGames().size(), 0);
    }

    @Test
    void getterTanggal(){
        assertEquals(transaksi.getTanggal(), tanggal);
    }

    @Test
    void getterEmail(){
        assertEquals(transaksi.getEmailPembeli(), "email");
    }

    @Test
    void getterTotalPrice(){
        assertEquals(transaksi.getTotalPrice(), 50000);
    }

    @Test
    void testToString(){
    assertNotNull(Transaksi.builder().toString());
    }
    @Test
    void testNoArgsConstructor(){
        assertNotNull(new Transaksi());
    }



}