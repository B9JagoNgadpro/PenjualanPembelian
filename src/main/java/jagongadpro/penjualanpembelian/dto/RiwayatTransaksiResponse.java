package jagongadpro.penjualanpembelian.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class RiwayatTransaksiResponse {
    private String id;

    private  List<GameTransaksiResponse> listGames;

    private Date tanggal;

    private  String emailPembeli;
    private double totalPrice;
}
