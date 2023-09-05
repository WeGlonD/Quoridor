package ddym_corp.quoridor.profile.domain;

import java.util.UUID;

public interface ProfileImageRepository {
    void save(Long uid, String url);
    void delete(Long uid);
    String findOne(Long uid);
}
