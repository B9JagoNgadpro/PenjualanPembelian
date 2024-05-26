package jagongadpro.penjualanpembelian.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
public class CreateTransaksiResponse {
    private String id;

    private List<GameForTransaksi> games;

    private  Integer total_harga;

    private String tanggal_pembayaran;

    private  String pembeli_id;

}
