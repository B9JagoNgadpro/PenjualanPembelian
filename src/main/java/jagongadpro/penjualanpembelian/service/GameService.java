package jagongadpro.penjualanpembelian.service;

import jagongadpro.penjualanpembelian.dto.CreateGameRequest;
import jagongadpro.penjualanpembelian.dto.FilterGameRequest;
import jagongadpro.penjualanpembelian.dto.GameResponse;

import java.util.List;

public interface GameService {
    public GameResponse create(CreateGameRequest request);
    public List<GameResponse> getAll();
    List<GameResponse> filter(FilterGameRequest request);

}
