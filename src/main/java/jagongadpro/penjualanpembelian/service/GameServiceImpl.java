package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Game game = new Game();
        game.setNama(request.getNama());
        game.setStok(request.getStok());
        game.setKategori(request.getKategori());
        game.setHarga(request.getHarga());
        game.setDeskripsi(request.getDeskripsi());
        gameRepository.save(game);
    }
}