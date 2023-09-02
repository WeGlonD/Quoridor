package ddym_corp.quoridor.match.domain.service;

import ddym_corp.quoridor.match.WaitingUser;
import ddym_corp.quoridor.match.service.MatchService;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
@SpringBootTest
class MatchServiceImplTest {

    @Autowired
    MatchService matchService;
    @Autowired
    UserRepository userRepository;

    @Test
    void findingTest() {
        //given
        WaitingUser user1 = new WaitingUser();
        user1.setUid(-1L);user1.setScore(50);
        user1.setGameID(null); user1.setGameType(1);
        System.out.println("user1 = " + user1);
        User uu1= userRepository.save(new User(-1L, "12", "12","12","12",0,0,0));
        user1.setUid(uu1.getUid());

        WaitingUser user2 = new WaitingUser();
        user2.setUid(-2L);user2.setScore(70);
        user2.setGameID(null); user2.setGameType(1);
        System.out.println("user2 = " + user2);
        User uu2= userRepository.save(new User(-2L, "13", "13","13","13",0,0,0));
        user2.setUid(uu2.getUid());

        //when
        Long gameID1 = matchService.findOpponent(user1.getUid(),user1.getGameType()).getGameId();
        Long gameID2 = matchService.findOpponent(user2.getUid(),user2.getGameType()).getGameId();
        Long gameID3 = matchService.findOpponent(user1.getUid(),user1.getGameType()).getGameId();


        //then
        assertThat(gameID1).isNull();
        assertThat(gameID2).isEqualTo(gameID3);
    }

}