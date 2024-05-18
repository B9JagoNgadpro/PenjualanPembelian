package jagongadpro.penjualanpembelian.repository;

import jagongadpro.penjualanpembelian.model.Transaksi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
    public List<Transaksi> findAllByEmailPembeli(String email);
}
