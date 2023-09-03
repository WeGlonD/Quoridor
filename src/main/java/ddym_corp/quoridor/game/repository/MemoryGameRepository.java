package ddym_corp.quoridor.game.repository;

import ddym_corp.quoridor.game.service.Room;
import ddym_corp.quoridor.history.sevice.HistoryService;
import ddym_corp.quoridor.ranking.domain.service.RankingService;
import ddym_corp.quoridor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemoryGameRepository implements GameRepository {
    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final RankingService rankingService;
    private static Map<Long, Room> gameRooms = new ConcurrentHashMap<>();;
    private static AtomicLong sequence = new AtomicLong(0);;
    private static AtomicLong dateInLong = new AtomicLong(0);;
    private static final Long mult = 100000000000L;

    /**
     * 모든 방 찾기
     * @return
     */
    @Override
    public List<Room> findAll() {
        return new ArrayList<>(gameRooms.values());
    }

    /**
     * gameId로 방 찾기
     * @param gameId
     * @return
     */
    @Override
    public Room findByGameId(Long gameId) {
        return gameRooms.get(gameId);
    }

    /**
     * 방 저장
     * @param
     * @return
     */
    @Override
    public Room save() {
        Room room = getRoom();
        gameRooms.put(room.getGameId(), room);
        return room;
    }

    /**
     * 방 만들기
     * @return
     */
    private Room getRoom() {
        Room room = new Room(userRepository, historyService, rankingService);
        room.setGameId(makeGameId());
        return room;
    }

    /**
     * 고유 gameId 만들기
     * @return
     */
    private Long makeGameId() {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        log.info("now date = {}",now.toString());
        long dateNow = (now.getYear() * 10000L + now.getMonthValue() * 100L + now.getDayOfMonth()) * mult;
        if (dateInLong.get() != dateNow) {
            dateInLong.set(dateNow);
            sequence.set(0);
        }
        sequence.set(sequence.get() + 1);

        return dateInLong.get() + sequence.get();
    }

    /**
     * for debug
     */
    @PostConstruct
    private void init() {
        gameRooms = new LinkedHashMap<>();
        Room room = new Room(userRepository, historyService, rankingService);
        room.setGameId(1L);
        gameRooms.put(1L, room);
    }
}
