package jagongadpro.penjualanpembelian.repository;

import jagongadpro.penjualanpembelian.model.Transaksi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
}
