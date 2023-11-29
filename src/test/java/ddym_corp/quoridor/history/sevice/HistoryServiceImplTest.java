package ddym_corp.quoridor.history.sevice;

import ddym_corp.quoridor.history.History;
import ddym_corp.quoridor.history.repository.HistoryRepository;
import ddym_corp.quoridor.history.utils.Histories20ResponseDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
@Slf4j
class HistoryServiceImplTest {

    private final HistoryService historyService;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    @Autowired
    public HistoryServiceImplTest(HistoryService historyService, HistoryRepository historyRepository, UserRepository userRepository) {
        this.historyService = historyService;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    @AfterEach
    void clear() {
        historyRepository.clearAll();
    }

    @Test
    void find20Histories(){

        User user0 = new User();
        User user1 = new User();
        user0.setName("dong");
        user1.setName("yang");
        user0 = userRepository.save(user0);
        user1 = userRepository.save(user1);

        //given
        for (int gameId = 0; gameId<10; gameId++){
            History history = new History((long) gameId, user1.getUid(), user0.getUid(), 10, 10, new Timestamp(System.currentTimeMillis()));
            historyService.makeHistory(history);
            historyService.updateWinner((long) gameId,user1.getUid());
        }
        for (int gameId = 10; gameId<25; gameId++){
            History history = new History((long) gameId, user0.getUid(), user1.getUid(), 10, 10, new Timestamp(System.currentTimeMillis()));
            historyService.makeHistory(history);
        }


        //when
        List<Histories20ResponseDto> histories = historyService.getHistories(user1.getUid(), 21L);
        //then
        log.info("histories : {}",histories);
        Assertions.assertThat(histories.get(0).getGameId()).isEqualTo(20L);
        Assertions.assertThat(histories.get(19).getGameId()).isEqualTo(1L);
        Assertions.assertThat(histories.get(19).getWin()).isEqualTo(true);
    }

}