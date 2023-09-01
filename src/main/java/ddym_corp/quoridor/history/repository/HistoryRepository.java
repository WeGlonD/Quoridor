package ddym_corp.quoridor.history.repository;

import ddym_corp.quoridor.history.History;

import java.util.ArrayList;

/**
 * 1. History 저장
 * 1.1 기보 업데이트
 * 2. gameId로 파인드
 * 3. stamp로 최근 20개 찾기
 */
public interface HistoryRepository {
    History save(History history);
    public History update(History history);
    History findByGameId(Long gameId);
    ArrayList<History> find20(Long uid, Long gameId);
}
