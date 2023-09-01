package ddym_corp.quoridor.match.repository;

import java.util.Optional;

public interface MatchRepository {

    void save(WaitingUser user);
    Optional<WaitingUser> findByuID(Long uid);
    WaitingUser findSimilar(int score, int gameType);
    void delete(Long uid);

    void update(WaitingUser user);
}
