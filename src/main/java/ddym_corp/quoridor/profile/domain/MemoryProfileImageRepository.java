package ddym_corp.quoridor.profile.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class MemoryProfileImageRepository implements ProfileImageRepository{
    private final Map<Long, String> store = new HashMap<>();
    @Override
    public void save(Long uid, String url) {
        store.put(uid, url);
    }

    @Override
    public void delete(Long uid) {
        store.remove(uid);
    }

    @Override
    public String findOne(Long uid) {
        return store.getOrDefault(uid, null);
    }
}
