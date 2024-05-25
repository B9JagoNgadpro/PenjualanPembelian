package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoTest {
    UserRequestDto userRequestDto = UserRequestDto.builder().email("abc").saldo(10000).status("status").username("").build();

    @Test
    void setterGetterEmail(){
        userRequestDto.setEmail("email");
        assertEquals("email", userRequestDto.getEmail());
    }

    @Test
    void setterGetterUserName(){
        userRequestDto.setUsername("username");
        assertEquals("username", userRequestDto.getUsername());
    }

    @Test
    void setterGetterSaldo(){
        userRequestDto.setSaldo(70000);
        assertEquals( 70000, userRequestDto.getSaldo());
    }
    @Test
    void setterGetterStatus(){
        userRequestDto.setStatus("ROLE_PEMBELI");
        assertEquals("ROLE_PEMBELI", userRequestDto.getStatus());
    }

    @Test
    void toStringTest(){
    assertNotNull(UserRequestDto.builder().toString());
    }


}