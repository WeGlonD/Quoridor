package ddym_corp.quoridor;

import ddym_corp.quoridor.gameRoom.service.GameRoom;
import ddym_corp.quoridor.history.History;
import ddym_corp.quoridor.history.repository.HistoryRepository;
import ddym_corp.quoridor.ranking.RankingUser;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        User user1 = new User(0L, "didwoah", "animalstory7@gmail.com", "jaemo", "1111", 0, 0, 0);
        User user11 = userRepository.save(user1);
        log.info("user1 uid: {}, name: {}", user11.getUid(), user11.getName());

        User user2 = new User(0L, "retomoo", "ssss", "dw", "1111", 0, 0, 0);
        User user22 = userRepository.save(user2);
        log.info("user2 uid: {}, name: {}", user22.getUid(), user22.getName());

        User user3 = new User(0L, "irsam78", "csyun121461@gmail.com", "minseok", "1111", 0, 0, 0);
        User user33 = userRepository.save(user3);
        log.info("user3 uid: {}, name: {}", user33.getUid(), user33.getName());


        for (int gameId = 0; gameId<10; gameId++){
            History history = new History((long) gameId, 2L, 1L, 10, 10, new Timestamp(System.currentTimeMillis()));
            historyRepository.save(history);
        }
        for (int gameId = 10; gameId<25; gameId++){
            History history = new History((long) gameId, 1L, 2L, 10, 10, new Timestamp(System.currentTimeMillis()));
            historyRepository.save(history);
        }
    }

}
