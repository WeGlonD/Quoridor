package ddym_corp.quoridor.user;

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
public class MemoryUserRepository implements UserRepository{

    private static Map<Long, User> store = new ConcurrentHashMap<>();
    private static long sequence = 0l;

    @Override
    public User save(User user) {
        user.setUid(++sequence);
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

    @PostConstruct
    public void init() {
        User user1 = new User(0L, "didwoah", "animalstory7@gmail.com", "jaemo", "1111", 0, 0, 0);
        User user11 = save(user1);
        log.info("user1 uid: {}, name: {}", user11.getUid(), user11.getName());

        User user2 = new User(0L, "retomoo", "ssss", "dw", "1111", 0, 0, 0);
        User user22 = save(user2);
        log.info("user2 uid: {}, name: {}", user22.getUid(), user22.getName());

        User user3 = new User(0L, "irsam78", "csyun121461@gmail.com", "minseok", "1111", 0, 0, 0);
        User user33 = save(user3);
        log.info("user3 uid: {}, name: {}", user33.getUid(), user33.getName());
    }

    @Override
    public void clearAll(){
        store.clear();
    }
}
