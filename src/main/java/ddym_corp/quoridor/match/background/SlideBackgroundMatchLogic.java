package ddym_corp.quoridor.match.background;

import ddym_corp.quoridor.gameRoom.service.GameRoom;
import ddym_corp.quoridor.gameRoom.service.GameRoomManager;
import ddym_corp.quoridor.history.History;
import ddym_corp.quoridor.history.sevice.HistoryService;
import ddym_corp.quoridor.match.foreground.MatchedUser;
import ddym_corp.quoridor.match.foreground.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlideBackgroundMatchLogic implements BackgroundMatchLogic{

    private final GameRoomManager roomManager;
    private final HistoryService historyService;
    private final MatchRepository matchRepository;

    LinkedList<PreMatchedUser> store = new LinkedList<>();

    @Override
    public void run(){
        MyThread myThread = new MyThread();
        myThread.start();
    }

    @Override
    public void join(PreMatchedUser queueUser) {
        store.add(queueUser);
    }

    private class MyThread extends Thread{
        @Override
        public void run() {
            while (true) {
                while (store.size() > 1) {
                    logic();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void logic() {
            PreMatchedUser first = store.getFirst();
            List<PreMatchedUser> users = store.subList(1, store.size());
            log.info("partner users : {}", users);
            Integer firstScore = first.getScore();
            int i = 1;
            int idx = 0;
            int minGap = 9999999;
            for (PreMatchedUser user : users) {
                int gap = Math.abs(user.getScore() - firstScore);
                if (gap < minGap) {
                    minGap = gap;
                    idx = i;
                }
                i++;
            }
            // Room 생성
            GameRoom newRoom = roomManager.createRoom();
            PreMatchedUser second = store.get(idx);
            newRoom.setUIDs(new Long[]{first.getUid(), second.getUid()});

            // 대기열에서 제거
            PreMatchedUser removeSecond = store.remove(idx);
            if (removeSecond != second) {
                log.error("removeSecond : {} second :  {}", removeSecond, second);
                throw new RuntimeException();
            }
            PreMatchedUser removeFirst = store.removeFirst();
            if (removeFirst != first) {
                log.error("removeFirst : {} second :  {}", removeFirst, first);
                throw new RuntimeException();
            }

            // make History
            History history = historyService.makeHistory(new History(newRoom.getGameId(), first.getUid(), second.getUid(), first.getScore(), second.getScore(), Timestamp.valueOf(LocalDateTime.now())));

            // push 2 MatchedUser to matchRepository store
            matchRepository.save(new MatchedUser(first.getUid(), 0, history.getGameId()));
            matchRepository.save(new MatchedUser(second.getUid(), 1, history.getGameId()));
            log.info("{} & {} match", first.getUid(), second.getUid());
            log.info("matchRepository save completed");
        }
    }
}