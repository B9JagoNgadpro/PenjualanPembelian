package jagongadpro.penjualanpembelian.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class TransaksiResponse {
    private String id;

    private Map<String, Integer> games;

    private Date tanggal;

    private  String emailPembeli;
    private double totalPrice;
}
