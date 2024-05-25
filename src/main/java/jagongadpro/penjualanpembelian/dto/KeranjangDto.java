package jagongadpro.penjualanpembelian.dto;


import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class KeranjangDto {
    private String email;

    private Map<String, Integer> items = new HashMap<>();

    private int totalPrice;

}
