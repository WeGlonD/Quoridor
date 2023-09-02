package ddym_corp.quoridor.match.repository;

import ddym_corp.quoridor.match.WaitingUser;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMatchRepository implements MatchRepository {

    private final Map<Long, WaitingUser> store = new ConcurrentHashMap<>();

    @Override
    public void save(WaitingUser user) {
        store.put(user.getUid(), user);
    }

    @Override
    public Optional<WaitingUser> findByuID(Long uid) {
        return Optional.ofNullable(store.get(uid));
    }

    @Override
    public WaitingUser findSimilar(int score, int gameType) {
        for (WaitingUser user : store.values()) {
            if(gameType == user.getGameType() && isAppropriateScore(score, user.getScore())){
                return user;
            }
        }
        return null;
    }

    boolean isAppropriateScore(int score1, int score2){
        int diff = score1 - score2;
        return -50 < diff && diff < 50;
    }

    @Override
    public void delete(Long uid) {
        store.remove(uid);
    }

    @Override
    public void update(WaitingUser user) {
        store.replace(user.getUid(), user);
    }
}
