package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.KeranjangDto;
import jagongadpro.penjualanpembelian.dto.TransaksiResponse;
import org.springframework.stereotype.Service;

@Service
public interface TransaksiService {
    public TransaksiResponse createTransaksi(KeranjangDto keranjang, String email, String token);
}
