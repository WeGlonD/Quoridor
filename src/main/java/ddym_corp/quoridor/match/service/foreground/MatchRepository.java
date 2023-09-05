package ddym_corp.quoridor.match.service.foreground;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Optional;

@Slf4j
@Repository
public class MatchRepository {
    LinkedList<MatchedUser> store = new LinkedList<>();

    public void save(MatchedUser matchedUser){
        store.add(matchedUser);
    }
    public void delete(Long uid){
        store.remove(uid);
    }
    public Optional<MatchedUser> findByUid(Long uid){
        return store.stream()
                .filter(matchedUser -> matchedUser.getUid() == uid)
                .findAny();
    }
}
