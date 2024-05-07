package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTransaksiResponseTest {
    GameTransaksiResponse gameTransaksiResponse = new GameTransaksiResponse();

    @Test
    void getterSetterNama(){
        gameTransaksiResponse.setNama("nama");
        assertEquals(gameTransaksiResponse.getNama(), "nama");
    }

    @Test
    void getterSetterQuantity(){
        gameTransaksiResponse.setQuantity(90);
        assertEquals(gameTransaksiResponse.getQuantity(), 90);
    }

    @Test
    void getterSetterHargaSatuan(){
        gameTransaksiResponse.setHargaSatuan(10000);
        assertEquals(gameTransaksiResponse.getHargaSatuan(), 10000);
    }

    @Test
    void getterSetterTotal(){
        gameTransaksiResponse.setTotal(100000);
        assertEquals(gameTransaksiResponse.getTotal(), 100000);
    }

}