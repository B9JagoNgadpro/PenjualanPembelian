package jagongadpro.penjualanpembelian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ListGameResponse {
    private List<GameTransaksiResponse> listGames;
    private double totalPrice;
}
