package ddym_corp.quoridor.user;

import ddym_corp.quoridor.user.bin.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JpaUserRepositoryTest {

    @Autowired
    JpaUserRepository jpaUserRepository;
    @Test
    void save() {
        //given
        User user = new User("id", "password");

        //when

        //then
    }

    @Test
    void findByuID() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByLoginId() {
    }
}