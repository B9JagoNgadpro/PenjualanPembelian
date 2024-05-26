package jagongadpro.penjualanpembelian.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jagongadpro.penjualanpembelian.dto.KeranjangDto;
import jagongadpro.penjualanpembelian.dto.RiwayatTransaksiResponse;
import jagongadpro.penjualanpembelian.dto.TransaksiResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TransaksiService {
    public void createTransaksi(KeranjangDto keranjang, String email, String token);
    public List<RiwayatTransaksiResponse>  getTransaksiByEmail(String email);
}
