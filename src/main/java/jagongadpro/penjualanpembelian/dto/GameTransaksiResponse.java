package jagongadpro.penjualanpembelian.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDisplayResponse {
    private String nama;
    private Integer quantity;
    private Integer hargaSatuan;
    private Integer total;
}
