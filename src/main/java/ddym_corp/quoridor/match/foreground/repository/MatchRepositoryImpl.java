package ddym_corp.quoridor.match.foreground.repository;

import ddym_corp.quoridor.match.foreground.MatchedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Optional;

@Slf4j
@Repository
public class MatchRepositoryImpl implements MatchRepository{
    LinkedList<MatchedUser> store = new LinkedList<>();
    @Override
    public void save(MatchedUser matchedUser){
        store.add(matchedUser);
    }
    @Override
    public void delete(Long uid){
        store.remove(uid);
    }
    @Override
    public Optional<MatchedUser> findByUid(Long uid){
        return store.stream()
                .filter(matchedUser -> matchedUser.getUid() == uid)
                .findAny();
    }
}
