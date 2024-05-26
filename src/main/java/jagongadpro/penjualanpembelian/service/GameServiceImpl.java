package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.FilterGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;
import jagongadpro.penjualanpembelian.dto.GameTransaksiResponse;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class GameServiceImpl implements GameService{
    @Autowired
    private ValidationService validationService;

    @Autowired
    private GameRepository gameRepository;
    @Override
    @Transactional
    public GameResponse create(CreateGameRequest request) {
        validationService.validate(request);
        Game game = new Game.GameBuilder().nama(request.getNama()).stok(request.getStok()).kategori(request.getKategori()).harga(request.getHarga()).deskripsi(request.getDeskripsi()).idPenjual(request.getIdPenjual()).build();
        gameRepository.save(game);
        return  toGameResponse(game);


    }
    private GameResponse toGameResponse(Game game) {
        return GameResponse.builder()
                .nama(game.getNama())
                .id(game.getId())
                .deskripsi(game.getDeskripsi())
                .harga(game.getHarga())
                .kategori(game.getKategori())
                .stok(game.getStok())
                .idPenjual(game.getIdPenjual())
                .build();
    }
    @Transactional(readOnly = true)
    public List<GameResponse> getAll() {
        List<Game> games = gameRepository.findAll();
        return games.stream().map(this::toGameResponse).toList();
    }
    
    @Transactional(readOnly = true)
    public List<GameResponse> filter(FilterGameRequest request) {
        Specification<Game> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(request.getNama())) {
                predicates.add(builder.like(builder.lower(root.get("nama")), "%" + request.getNama().toLowerCase() + "%"));
            }
            if (Objects.nonNull(request.getHarga())) {
                predicates.add(builder.equal(root.get("harga"), request.getHarga()));
            }
            if (Objects.nonNull(request.getKategori())) {
                predicates.add(builder.like(builder.lower(root.get("kategori")), "%" + request.getKategori().toLowerCase() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };

        List<Game> games = gameRepository.findAll(specification);
        return games.stream().map(this::toGameResponse).toList();

    }

    @Transactional(readOnly = true)
    public GameResponse getById(String id) {
        Game game = gameRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Game tidak ditemukan"));
        return toGameResponse(game);
    }

    @Transactional(readOnly = true)
    public GameTransaksiResponse countGamePrice(String key, Integer quantity){
        Game game = gameRepository.findById(key).orElseThrow( ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Game tidak ditemukan"));
        Integer total = quantity * game.getHarga();
        GameTransaksiResponse gameTransaksiResponse = new GameTransaksiResponse();
        gameTransaksiResponse.setNama(game.getNama());
        gameTransaksiResponse.setQuantity(quantity);
        gameTransaksiResponse.setTotal(total);
        gameTransaksiResponse.setHargaSatuan(game.getHarga());
        return gameTransaksiResponse;
    }


}