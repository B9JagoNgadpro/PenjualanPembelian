package jagongadpro.penjualanpembelian.repository;

import jagongadpro.penjualanpembelian.model.Game;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game,String>, JpaSpecificationExecutor<Game> {
}
