package jagongadpro.penjualanpembelian.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;


@Getter
@Builder
public class RiwayatTransaksiResponse {
    private String id;

    private  List<GameTransaksiResponse> listGames;

    private Date tanggal;

    private  String emailPembeli;
    private double totalPrice;
}
