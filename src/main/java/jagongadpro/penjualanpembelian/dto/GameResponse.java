package jagongadpro.penjualanpembelian.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GameResponse {
    private String id;

    private String nama;

    private String deskripsi;


    private Integer harga;

    private String kategori;


    private Integer stok;

    private  String idPenjual;
}
