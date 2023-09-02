package ddym_corp.quoridor.game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddym_corp.quoridor.auth.web.SessionConst;
import ddym_corp.quoridor.game.web.MoveMessage;
import ddym_corp.quoridor.history.sevice.HistoryService;
import ddym_corp.quoridor.ranking.domain.service.RankingService;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static ddym_corp.quoridor.auth.web.SessionConst.USER_ID;
import static ddym_corp.quoridor.game.service.GameResultType.*;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class Room {

    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final RankingService rankingService;

    private Set<WebSocketSession> sessions = new HashSet<>(2);
    private Long[] uIDs;
    private int turn = 0;

    private Long gameId;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void handleTempActions(WebSocketSession session, MoveMessage moveMessage) {
        Long requestUID = (Long)session.getAttributes().get(USER_ID);
        if (!uIDs[0].equals(requestUID) && !uIDs[1].equals(requestUID))
            return;
        if(moveMessage.getType() == 9) {
            sessions.add(session);
            return;
        }

        log.info("sessions size : {}",sessions.size());

        // 필요한 처리 ( ■ : 테스트 완료 / □ : 구현완료 / X : 미완성 )
        // ■ 자기 차례에 맞게 요청이 들어왔는지 확인하고 메시지 보내기
        // ■ 메시지 보내면 턴 넘기기
        // □ 게임이 끝나는 수가 들어오면 점수계산하고 UserRepository에 점수, 승패 저장, 웹소켓 닫기.
        // □ 수가 들어올 때마다 History를 DB에 저장.
        // □ 이 게임에 배정되지 않은 유저가 호출한 경우 처리.
        if (getTurn(session) == turn) {
            sendMoveMessage(moveMessage, session);
            String moveStr = moveMessage.getType().toString() + moveMessage.getRow().toString() + moveMessage.getCol().toString();
            log.info("UID {} move {}", uIDs[turn], moveStr);
            historyService.updateMove(gameId, moveStr);

            if (moveMessage.getType() > 2) {
                // 게임이 끝났다는 정보가 들어왔을 때.
                Long winnerUID = null, loserUID = null;
                if (moveMessage.getType() == 3) {
                    // 이긴 수 : 상대방 진영까지 말을 움직이는 수가 들어옴.
                    winnerUID = uIDs[turn];
                    loserUID = uIDs[(turn + 1) % 2];
                }
                else if (moveMessage.getType() == 4) {
                    // 진 수 : 시간초과로 패배
                    loserUID = uIDs[turn];
                    winnerUID = uIDs[(turn + 1) % 2];
                }
                calcDeltaScore(winnerUID, loserUID);
                sessions.forEach(session1 -> {
                    try {
                        session1.close();
                    } catch (IOException e) {
                        log.error("Cannot close WebSocketSession");
                        throw new RuntimeException(e);
                    }
                });
            }

            turn = (turn + 1) % 2;
        }
    }

    /**
     * sessions에 있는 상대방 member에 moveMessage를 보내는 메서드 service(domain)
     * @param message
     * @param session message를 보낸 사람의 session
     * @param <T>
     */
    public <T> void sendMoveMessage(T message, WebSocketSession session) {
        sessions.parallelStream().forEach(s -> {
            if (session != s)
                send(s, message);
        });
    }

    /**
     * 메세지가 보내는 메서드 controller(web)
     * @param session
     * @param message
     * @param <T>
     */
    public <T> void send(WebSocketSession session, T message) {
        try {
            log.info("send message to user {} : {}", session.getAttributes().get(USER_ID).toString(), message.toString());
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * session이 가진 turn을 얻는 메서드(web)
     * @param session
     * @return
     */
    private Integer getTurn(WebSocketSession session){
        return (Integer) session.getAttributes().get(SessionConst.TURN);
    }

    /**
     * 경기 결과 두 플레이어의 변경된 점수를 계산하고, 유저 리포지토리에 점수, 승수, 전체 게임수를 업데이트.
     * 변경된 점수를 각 유저의 웹소켓세션을 이용해 메시지로 전송.
     * @param winnerUID
     * @param loserUID
     */
    private void calcDeltaScore(Long winnerUID, Long loserUID) {
        User winner = userRepository.findByuID(winnerUID).get();
        User loser = userRepository.findByuID(loserUID).get();
        Integer winPreScore = winner.getScore();
        Integer losePreScore = loser.getScore();
        int winnerScore = Elo.calcRating(winPreScore, losePreScore, WIN);
        int loserScore = Elo.calcRating(losePreScore, winPreScore, LOSE);
        winner.setScore(winnerScore); winner.setTotalGames(winner.getTotalGames() + 1); winner.setWinGames(winner.getWinGames() + 1);
        loser.setScore(loserScore); loser.setTotalGames(loser.getTotalGames() + 1);
        userRepository.update(winner);
        userRepository.update(loser);
        rankingService.update(winnerUID, winPreScore, winnerScore);
        rankingService.update(loserUID, losePreScore, loserScore);

        sendScore(winnerUID,loserUID,winnerScore,loserScore, winner.getTotalGames(),loser.getTotalGames(),winner.getWinGames(),loser.getWinGames());

    }

    private void sendScore(Long winnerUID, Long loserUID, int winnerScore, int loserScore, int winnerTotal, int loserTotal, int winnerWins, int loserWins) {
        sessions.parallelStream().forEach(session -> {
            if (session.getAttributes().get(USER_ID).equals(winnerUID)) {
                send(session, new MoveMessage(-1, (long) winnerScore,winnerTotal,winnerWins));
            }
            else if (session.getAttributes().get(USER_ID).equals(loserUID)) {
                send(session, new MoveMessage(-1, (long) loserScore,loserTotal,loserWins));
            }
        });
    }
}
