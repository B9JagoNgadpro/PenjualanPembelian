package jagongadpro.penjualanpembelian.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.MapKeyColumn;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class KeranjangDto {
    private String email;

    private Map<String, Integer> items = new HashMap<>();

    private double totalPrice;

}
