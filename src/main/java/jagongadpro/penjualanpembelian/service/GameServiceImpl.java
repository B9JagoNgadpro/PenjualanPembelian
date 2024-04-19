package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameServiceImpl implements GameService{
    @Autowired
    private ValidationService validationService;

    @Autowired
    private GameRepository gameRepository;
    @Override
    @Transactional
    public void create(CreateGameRequest request) {
        validationService.validate(request);
        Game game = new Game.GameBuilder().nama(request.getNama()).stok(request.getStok()).kategori(request.getKategori()).harga(request.getHarga()).deskripsi(request.getDeskripsi()).build();
        gameRepository.save(game);

    }
}
