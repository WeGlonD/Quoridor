package ddym_corp.quoridor.user.bin;

//import org.springframework.data.jpa.repository.JpaRepository;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;

import java.util.Optional;

public interface SpringDataJpaUserRepository extends /*JpaRepository<User, Long>,*/ UserRepository {
    @Override
    Optional<User> findByLoginId(String loginId);
    @Override
    Optional<User> findByuID(Long uID);
}
