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
        assertEquals("id", riwayatTransaksiResponse.getId());
    }

    @Test
    void getterListGames(){
    assertNotNull(riwayatTransaksiResponse.getListGames());
    }

    @Test
    void getterTanggal(){
       assertEquals(tanggal,riwayatTransaksiResponse.getTanggal());
    }

    @Test
    void getterEmailPembeli(){
        assertEquals("email", riwayatTransaksiResponse.getEmailPembeli());
    }
    @Test
    void getterTotalPrice(){
        assertEquals(10000, riwayatTransaksiResponse.getTotalPrice());
    }

    @Test
    void testToString(){
        assertNotNull(RiwayatTransaksiResponse.builder().toString());
    }
}