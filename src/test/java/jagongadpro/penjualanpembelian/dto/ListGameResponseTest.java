package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListGameResponseTest {
    ListGameResponse listGameResponse = ListGameResponse.builder().listGames(new ArrayList<>()).totalPrice(10000).build();
    @Test
    void getListGames(){
        assertNotNull(listGameResponse.getListGames());
    }

    @Test
    void getTotalPrice(){
        assertEquals(listGameResponse.getTotalPrice(), 10000);
    }

    @Test
    void testToString(){
        assertNotNull(ListGameResponse.builder().toString());
    }

}