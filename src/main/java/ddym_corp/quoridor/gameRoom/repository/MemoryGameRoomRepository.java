package ddym_corp.quoridor.gameRoom.repository;

import ddym_corp.quoridor.gameRoom.service.GameRoom;
import ddym_corp.quoridor.history.sevice.HistoryService;
import ddym_corp.quoridor.ranking.domain.service.RankingService;
import ddym_corp.quoridor.user.repository.UserRepository;
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
public class MemoryGameRoomRepository implements GameRoomRepository {
    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final RankingService rankingService;
    private static Map<Long, GameRoom> gameRooms = new ConcurrentHashMap<>();;
    private static AtomicLong sequence = new AtomicLong(0);;
    private static AtomicLong dateInLong = new AtomicLong(0);;
    private static final Long mult = 100000000000L;

    /**
     * 모든 방 찾기
     * @return
     */
    @Override
    public List<GameRoom> findAll() {
        return new ArrayList<>(gameRooms.values());
    }

    /**
     * gameId로 방 찾기
     * @param gameId
     * @return
     */
    @Override
    public GameRoom findByGameId(Long gameId) {
        return gameRooms.get(gameId);
    }

    /**
     * gameId로 방 삭제
     * @param gameId
     * @return 방 지웠으면 true, 방이 원래 없었으면 false
     */
    @Override
    public boolean remove(Long gameId) {
        GameRoom removed = gameRooms.remove(gameId);
        if (removed != null) return true;
        else return false;
    }

    /**
     * 방 저장
     * @param
     * @return
     */
    @Override
    public GameRoom save() {
        GameRoom room = getRoom();
        gameRooms.put(room.getGameId(), room);
        return room;
    }

    /**
     * 방 만들기
     * @return
     */
    private GameRoom getRoom() {
        GameRoom room = new GameRoom(userRepository, historyService, rankingService);
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
        GameRoom room = new GameRoom(userRepository, historyService, rankingService);
        room.setGameId(1L);
        gameRooms.put(1L, room);
    }
}
