package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTransaksiResponseTest {
    GameTransaksiResponse gameTransaksiResponse = new GameTransaksiResponse();

    @Test
    void getterSetterNama(){
        gameTransaksiResponse.setNama("nama");
        assertEquals("nama", gameTransaksiResponse.getNama());
    }

    @Test
    void getterSetterQuantity(){
        gameTransaksiResponse.setQuantity(90);
        assertEquals( 90, gameTransaksiResponse.getQuantity());
    }

    @Test
    void getterSetterHargaSatuan(){
        gameTransaksiResponse.setHargaSatuan(10000);
        assertEquals(10000, gameTransaksiResponse.getHargaSatuan() );
    }

    @Test
    void getterSetterTotal(){
        gameTransaksiResponse.setTotal(100000);
        assertEquals(100000, gameTransaksiResponse.getTotal());
    }

}