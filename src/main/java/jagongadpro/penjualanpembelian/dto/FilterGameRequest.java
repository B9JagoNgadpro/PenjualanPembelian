package jagongadpro.penjualanpembelian.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FilterGameRequest {
    String nama;
    String kategori;
    Integer harga;
}
