package ddym_corp.quoridor.profile.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Repository
public class MemoryProfileImageRepository implements ProfileImageRepository{
    private final Map<Long, String> store = new HashMap<>();
    @Override
    public void save(Long uid, String url) {
        log.info("MemoryProfileImageRepository - save - uid: {}, url: {}", uid, url);
        store.put(uid, url);
    }

    @Override
    public void delete(Long uid) {
        log.info("MemoryProfileImageRepository - delete - uid: {}", uid);
        log.info("found uid: {}, url: {}", uid, store.getOrDefault(uid, null));
        store.remove(uid);
    }

    @Override
    public String findOne(Long uid) {
        log.info("MemoryProfileImageRepository - findOne - uid: {}", uid);
        String url = store.getOrDefault(uid, null);
        log.info("found uid: {}, url: {}", uid, url);
        return url;
    }
}
