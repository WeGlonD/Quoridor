package ddym_corp.quoridor.history.sevice;

import ddym_corp.quoridor.auth.domain.login.LoginServiceImpl;
import ddym_corp.quoridor.history.History;
import ddym_corp.quoridor.history.repository.HistoryRepository;
import ddym_corp.quoridor.history.utils.Histories20ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository historyRepository;
    private final LoginServiceImpl loginService;
    @Override
    public History makeHistory(History history) {
        return historyRepository.save(history);
    }

    @Override
    public History updateMove(Long gameId, String move) {
        History history = historyRepository.findByGameId(gameId);
        String moves = history.getMoves()+"/"+move;
        history.setMoves(moves);
        return historyRepository.update(history);
    }

    @Override
    public History updateWinner(Long gameId, Long winUserId) {
        History history = historyRepository.findByGameId(gameId);
        history.setWinnerId(winUserId);
        return historyRepository.update(history);
    }

    @Override
    public History getOneHistory(Long gameId) {
        return historyRepository.findByGameId(gameId);
    }

    @Override
    public List<Histories20ResponseDto> getHistories(Long uid, Long gameId) {
        return historyRepository.find20(uid, gameId).stream()
                .map(history -> new Histories20ResponseDto(history.getGameId(), history.getWinnerId()==uid, loginService.getName((history.getUid0()==uid)?history.getUid0():history.getUid1()), loginService.getScore((history.getUid0()==uid)?history.getUid0():history.getUid1())))
                .collect(Collectors.toList());
    }
}
