package ddym_corp.quoridor.match.service.foreground;

import ddym_corp.quoridor.match.utils.MatchResponseDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class MatchServiceTest {
    @Autowired
    MatchService matchService;

    @Autowired
    UserRepository userRepository;

    @Test
    void match() throws InterruptedException {
        //given
        User user0 = userRepository.save(new User());
        User user1 = userRepository.save(new User());

        user0.setScore(400);
        userRepository.update(user0);
        user1.setScore(500);
        userRepository.update(user1);

        matchService.join(user0.getUid(),400);
        matchService.join(user1.getUid(),500);

        Thread.sleep(5000);
        //when
        MatchResponseDto dto1 = matchService.check(user0.getUid());
        MatchResponseDto dto2 = matchService.check(user1.getUid());
        //then
        assertThat(dto1.getOpponentScore()).isEqualTo(500);
        assertThat(dto2.getOpponentScore()).isEqualTo(400);
        assertThat(dto1.getTurn()).isNotEqualTo(dto2.getTurn());
    }
}