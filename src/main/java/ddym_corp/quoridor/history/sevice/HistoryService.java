package ddym_corp.quoridor.history.sevice;

import ddym_corp.quoridor.history.History;
import ddym_corp.quoridor.history.utils.Histories20ResponseDto;

import java.util.List;

public interface HistoryService {
    History makeHistory(History history);
    History updateMove(Long gameId, String move);
    History updateWinner(Long gameId, Long winUserId);
    History getOneHistory(Long gameId);
    List<Histories20ResponseDto> getHistories(Long uid, Long gameId);
}
