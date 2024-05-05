package jagongadpro.penjualanpembelian.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoTest {
    UserRequestDto userRequestDto = UserRequestDto.builder().email("abc").saldo(10000).status("status").username("").build();

    @Test
    void setterGetterEmail(){
        userRequestDto.setEmail("email");
        assertEquals(userRequestDto.getEmail(), "email");
    }

    @Test
    void setterGetterUserName(){
        userRequestDto.setUsername("username");
        assertEquals(userRequestDto.getUsername(), "username");
    }

    @Test
    void setterGetterSaldo(){
        userRequestDto.setSaldo(70000);
        assertEquals(userRequestDto.getSaldo(), 70000);
    }
    @Test
    void setterGetterStatus(){
        userRequestDto.setStatus("ROLE_PEMBELI");
        assertEquals(userRequestDto.getStatus(), "ROLE_PEMBELI");
    }

    @Test
    void toStringTest(){
    assertNotNull(UserRequestDto.builder().toString());
    }


}