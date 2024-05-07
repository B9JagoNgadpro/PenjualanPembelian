package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class KeranjangDtoTest {
    KeranjangDto keranjangDto= new KeranjangDto();

    @Test
    void getterSetterEmail(){
       keranjangDto.setEmail("email");
        assertEquals(keranjangDto.getEmail(), "email");
    }

    @Test
    void getterSetterItems(){
        keranjangDto.setItems(new HashMap<>());
        assertNotNull(keranjangDto.getItems());
    }

    @Test
    void getterSetterTotalPrice(){
        keranjangDto.setTotalPrice(90000);
        assertEquals(keranjangDto.getTotalPrice(), 90000);
    }



}