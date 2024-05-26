package jagongadpro.penjualanpembelian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GameForTransaksi {
    private String id;

    private String nama;

    private String deskripsi;


    private Integer harga;

    private String kategori;

    private  String penjual_id;
}
