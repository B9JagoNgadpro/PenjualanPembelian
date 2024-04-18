package jagongadpro.penjualanpembelian.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String nama;

    String deskripsi;


    Integer harga;

    String kategori;


    Integer stok;

}
