package ddym_corp.quoridor.match.foreground.repository;

import ddym_corp.quoridor.match.foreground.MatchedUser;

import java.util.Optional;

public interface MatchRepository {
    void save(MatchedUser matchedUser);
    void delete(Long uid);
    Optional<MatchedUser> findByUid(Long uid);
}
