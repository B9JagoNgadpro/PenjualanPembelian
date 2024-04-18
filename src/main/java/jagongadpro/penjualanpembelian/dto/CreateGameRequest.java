package jagongadpro.penjualanpembelian.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameRequest {
    @NotBlank
    String nama;

    String deskripsi;

    @Positive
    @NotNull
    Integer harga;

    String kategori;

    @PositiveOrZero
    @NotNull
    Integer stok;

}
