package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.FilterGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;
import jagongadpro.penjualanpembelian.model.Game;
import jagongadpro.penjualanpembelian.repository.GameRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
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
        Game game = new Game.GameBuilder().nama(request.getNama()).stok(request.getStok()).kategori(request.getKategori()).harga(request.getHarga()).deskripsi(request.getDeskripsi()).build();
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
            //predicates.add(builder.equal(root.get("user"), user));
            if (Objects.nonNull(request.getNama())) {
                predicates.add(builder.like(root.get("nama"), "%" + request.getNama() + "%"));
            }
            if (Objects.nonNull(request.getHarga())) {
                predicates.add(builder.equal(root.get("harga"), request.getHarga() ));
            }
            if (Objects.nonNull(request.getKategori())) {
                predicates.add(builder.like(root.get("kategori"), "%" + request.getKategori() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        List<Game> games = gameRepository.findAll(specification);
        List<GameResponse> response = games.stream().map(this::toGameResponse).toList();
        return  response;
    }

    @Transactional(readOnly = true)
    public GameResponse getById(String id) {
        Game game = gameRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Game tidak ditemukan"));
        return toGameResponse(game);
    }


}
