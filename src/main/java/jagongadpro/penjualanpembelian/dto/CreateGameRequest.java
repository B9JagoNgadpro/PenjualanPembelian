package jagongadpro.penjualanpembelian.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameRequest {
    @Id
    String id;

    @NotBlank
    String nama;

    String deskripsi;

    @Positive
    Integer harga;

    String kategori;

    @PositiveOrZero
    Integer stok;

}
