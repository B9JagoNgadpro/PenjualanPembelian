package jagongadpro.penjualanpembelian.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaksi {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @ElementCollection
    @CollectionTable(name = "transaction_games") // Tabel untuk menyimpan list game dalam transaksi
    @MapKeyColumn(name = "transaction_id") // Kolom untuk game Id
    @Column(name = "quantity") // Kolom untuk quantity
    private Map<String, Integer> games;

    @Temporal(TemporalType.DATE)
    private Date tanggal = new Date();

    private  String emailPembeli;
    private double totalPrice;

}
