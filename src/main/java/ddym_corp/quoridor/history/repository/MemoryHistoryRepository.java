package ddym_corp.quoridor.history.repository;

import ddym_corp.quoridor.history.History;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryHistoryRepository implements HistoryRepository{

    private final Map<Long, History> store = new ConcurrentHashMap<>();

    @Override
    public History save(History history) {
        store.put(history.getGameId(), history);
        return store.get(history.getGameId());
    }
    @Override
    public History update(History history) {
        store.replace(history.getGameId(), history);
        return store.get(history.getGameId());
    }

    @Override
    public History findByGameId(Long gameId) {
        return store.get(gameId);
    }

    private ArrayList<History> findAll(){
        return new ArrayList<>(store.values());
    }

    @Override
    public ArrayList<History> find20(Long uid, Long gameId) {
        ArrayList<History> histories = findAll();
        return (ArrayList<History>) histories.stream()
                .filter(history -> (history.getUid1() == uid) || (history.getUid0() == uid))
                .filter(history -> history.getGameId()<gameId)
                .sorted(Comparator.comparingLong(History::getGameId).reversed()) // Sort by gameId
                .limit(20) // Select top 20
                .collect(Collectors.toList());
    }
}
