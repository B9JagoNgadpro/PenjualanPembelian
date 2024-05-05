package jagongadpro.penjualanpembelian.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserRequestDto {
    String email;
    String username;
    Integer saldo;
    String status;
}
