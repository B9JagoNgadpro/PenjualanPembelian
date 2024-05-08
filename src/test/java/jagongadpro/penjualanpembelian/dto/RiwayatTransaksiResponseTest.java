package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RiwayatTransaksiResponseTest {
    Date tanggal = new Date();
    RiwayatTransaksiResponse riwayatTransaksiResponse = RiwayatTransaksiResponse.builder().id("id").tanggal(tanggal).totalPrice(10000).emailPembeli("email").listGames(new ArrayList<>()).build();

    @Test
    void getterId(){
        assertEquals(riwayatTransaksiResponse.getId(), "id");
    }

    @Test
    void getterListGames(){
    assertNotNull(riwayatTransaksiResponse.getListGames());
    }

    @Test
    void getterTanggal(){
       assertEquals(riwayatTransaksiResponse.getTanggal(), tanggal);
    }

    @Test
    void getterEmailPembeli(){
        assertEquals(riwayatTransaksiResponse.getEmailPembeli(), "email");
    }
    @Test
    void getterTotalPrice(){
        assertEquals(riwayatTransaksiResponse.getTotalPrice(), 10000);
    }

    @Test
    void testToString(){
        assertNotNull(RiwayatTransaksiResponse.builder().toString());
    }
}