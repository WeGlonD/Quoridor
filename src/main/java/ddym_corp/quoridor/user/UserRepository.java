package ddym_corp.quoridor.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByuID(Long uID);
    Optional<User> findByLoginId(String loginId);
    User update(User user);
//    User delete(Long uID);

    Optional<User> findByName(String name);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    void clearAll();
}
