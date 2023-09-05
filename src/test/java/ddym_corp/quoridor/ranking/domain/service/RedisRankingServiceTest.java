package ddym_corp.quoridor.ranking.domain.service;

import ddym_corp.quoridor.ranking.RankingUser;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class RedisRankingServiceTest {
    @Autowired RankingService rankingService;
    @Autowired UserRepository userRepository;

    private Long uid1, uid2, uid3;

    @BeforeEach
    void set1() {
        log.info("userRepository {}", userRepository.findAll());
        userRepository.clearAll();
        rankingService.removeAll();

        User user1 = new User(0L, "didwoah", "animalstory7@gmail.com", "jaemo", "1111", 0, 0, 0);
        User user11 = userRepository.save(user1);
        log.info("user1 uid: {}, name: {}", user11.getUid(), user11.getName());
        uid1 = user11.getUid();

        User user2 = new User(0L, "retomoo", "ssss", "dw", "1111", 0, 0, 0);
        User user22 = userRepository.save(user2);
        log.info("user2 uid: {}, name: {}", user22.getUid(), user22.getName());
        uid2 = user22.getUid();

        User user3 = new User(0L, "irsam78", "csyun121461@gmail.com", "minseok", "1111", 0, 0, 0);
        User user33 = userRepository.save(user3);
        log.info("user3 uid: {}, name: {}", user33.getUid(), user33.getName());
        uid3 = user33.getUid();

        rankingService.join(uid1);
        rankingService.join(uid2);
        rankingService.join(uid3);
        rankingService.update(uid1, 0, 20);
        rankingService.update(uid2, 0, 40);
        rankingService.update(uid3, 0, 30);
    }

    @AfterEach
    void rollback() {
        rankingService.removeAll();
        userRepository.clearAll();
    }

    @Test
    void downwardTest() {

        //평범한 쿼리
        List<RankingUser> downward32 = rankingService.findDownward(uid3, 2);
        assertThat(downward32.get(1).getUid()).isEqualTo(uid1);

        //하나 쿼리
        List<RankingUser> downward31 = rankingService.findDownward(uid3, 1);
        assertThat(downward31.get(0).getUid()).isEqualTo(uid3);

        //사이즈 오버된 쿼리
        List<RankingUser> downward25 = rankingService.findDownward(uid2, 5);
        assertThat(downward25.size()).isEqualTo(3);

        //꼴지보다 아래 쿼리
        List<RankingUser> downward14 = rankingService.findDownward(uid1, 4);
        assertThat(downward14.size()).isEqualTo(1);
    }

    @Test
    void upwardTest() {

        //평범한 쿼리
        List<RankingUser> upward12 = rankingService.findUpward(uid1, 2);
        assertThat(upward12.get(0).getUid()).isEqualTo(uid2);

        //하나 쿼리
        List<RankingUser> upward11 = rankingService.findUpward(uid1, 1);
        assertThat(upward11.get(0).getUid()).isEqualTo(uid3);

        //사이즈 오버된 쿼리
        List<RankingUser> upward15 = rankingService.findUpward(uid1, 5);
        assertThat(upward15.size()).isEqualTo(2);

        //1등보다 위 쿼리
        List<RankingUser> upward24 = rankingService.findUpward(uid2, 4);
        assertThat(upward24.size()).isEqualTo(0);
    }

    @Test
    void aroundTest(){

        //1등 위아래 쿼리
        List<RankingUser> around2 = rankingService.find20AroundMe(uid2);
        assertThat(around2.size()).isEqualTo(3);

        //2등 위아래 쿼리
        List<RankingUser> around3 = rankingService.find20AroundMe(uid3);
        assertThat(around3.size()).isEqualTo(3);

        //3등 위아래 쿼리
        List<RankingUser> around1 = rankingService.find20AroundMe(uid1);
        assertThat(around1.size()).isEqualTo(3);
    }
}