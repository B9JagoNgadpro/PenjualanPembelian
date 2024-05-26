package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CreateTransaksiResponseTest {


    CreateTransaksiResponse createTransaksiResponse = CreateTransaksiResponse.builder().id("id").tanggal_pembayaran("tanggal").total_harga(10000).pembeli_id("email").games(new ArrayList<>()).build();

    @Test
    void getterId(){
        assertEquals("id", createTransaksiResponse.getId());
    }

    @Test
    void getterListGames(){
        assertNotNull(createTransaksiResponse.getGames());
    }

    @Test
    void getterTanggal(){
        assertEquals("tanggal",createTransaksiResponse.getTanggal_pembayaran());
    }

    @Test
    void getterEmailPembeli(){
        assertEquals("email", createTransaksiResponse.getPembeli_id());
    }
    @Test
    void getterTotalPrice(){
        assertEquals(10000, createTransaksiResponse.getTotal_harga());
    }

    @Test
    void testToString(){
        assertNotNull(CreateTransaksiResponse.builder().toString());
    }
}