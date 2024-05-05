package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransaksiResponseTest {
    Date tanggal = new Date();
    Map<String,Integer> games = new HashMap<>();
    TransaksiResponse transaksiResponse = TransaksiResponse.builder().id("abc").games(games).emailPembeli("abc@gmail.com").totalPrice(70000).tanggal(tanggal).build();

    @Test
    void getGames(){
        assertEquals(transaksiResponse.getGames().size(), 0);
    }


    @Test
    void getId(){
        assertEquals(transaksiResponse.getId(), "abc");
    }

    @Test
    void getTanggal(){
        assertEquals(transaksiResponse.getTanggal(), tanggal);
    }

    @Test
    void getEmailPembeli(){
        assertEquals(transaksiResponse.getEmailPembeli(), "abc@gmail.com");
    }
    @Test
    void getTotalPrice(){
        assertEquals(transaksiResponse.getTotalPrice(), 70000);
    }

    @Test
    void toStringTest(){
        assertNotNull(TransaksiResponse.builder().toString());
    }
}