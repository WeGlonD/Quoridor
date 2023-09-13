package ddym_corp.quoridor.user.repository;

import ddym_corp.quoridor.gameRoom.service.scoreLogic.Elo;
import ddym_corp.quoridor.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemoryUserRepository implements UserRepository {

    private static Map<Long, User> store = new ConcurrentHashMap<>();
    private static long sequence = 0l;

    @Override
    public User save(User user) {
        user.setUid(++sequence);
        user.setScore(Elo.INIT_SCORE);
        store.put(user.getUid(), user);
        return user;
    }

    @Override
    public Optional<User> findByuID(Long uID) {
        log.info("findByuID uID = {}",uID);
        return Optional.ofNullable(store.get(uID));
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(user -> user.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public User update(User user) {
        store.replace(user.getUid(), user);
        return user;
    }

    @Override
    public Optional<User> findByName(String name) {
        return findAll().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
    @Override
    public void clearAll(){
        store.clear();
    }
}
