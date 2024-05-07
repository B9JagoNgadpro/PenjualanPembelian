package jagongadpro.penjualanpembelian.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
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
