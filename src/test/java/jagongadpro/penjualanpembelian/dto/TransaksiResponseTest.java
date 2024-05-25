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
        assertEquals(0, transaksiResponse.getGames().size());
    }


    @Test
    void getId(){
        assertEquals("abc", transaksiResponse.getId());
    }

    @Test
    void getTanggal(){
        assertEquals(tanggal, transaksiResponse.getTanggal());
    }

    @Test
    void getEmailPembeli(){
        assertEquals("abc@gmail.com", transaksiResponse.getEmailPembeli());
    }
    @Test
    void getTotalPrice(){
        assertEquals(70000, transaksiResponse.getTotalPrice());
    }

    @Test
    void toStringTest(){
        assertNotNull(TransaksiResponse.builder().toString());
    }
}