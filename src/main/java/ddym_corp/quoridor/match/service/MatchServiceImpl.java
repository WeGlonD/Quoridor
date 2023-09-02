package ddym_corp.quoridor.match.service;

import ddym_corp.quoridor.auth.domain.login.LoginServiceImpl;
import ddym_corp.quoridor.game.service.Room;
import ddym_corp.quoridor.game.service.RoomManager;
import ddym_corp.quoridor.history.History;
import ddym_corp.quoridor.history.sevice.HistoryService;
import ddym_corp.quoridor.match.WaitingUser;
import ddym_corp.quoridor.match.repository.MatchRepository;
import ddym_corp.quoridor.match.utils.MatchResponseDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final RoomManager roomManager;
    private final UserRepository userRepository;
    private final HistoryService historyService;
    private final LoginServiceImpl loginService;

    @Override
    public MatchResponseDto findOpponent(Long uid, int gameType) {
        Optional<WaitingUser> user = matchRepository.findByuID(uid);
        if(user.isPresent()){
            Long gameID = user.get().getGameID();//널: 매칭안됨, 아니면: 매칭됨
            Integer turn = user.get().getTurn();
            Long opponentUid = user.get().getOpponentUid();
            if(gameID != null) matchRepository.delete(uid);

            return new MatchResponseDto(gameID, turn, loginService.getName(opponentUid), loginService.getScore(opponentUid));
        }
        else {
            //uid로 user찾기
            Optional<User> optUser = userRepository.findByuID(uid);
            if (optUser.isEmpty()){
                throw new IllegalArgumentException("사용자 id가 유효하지 않습니다.");
            }

            int score = optUser.get().getScore();
            WaitingUser partner = matchRepository.findSimilar(score, gameType);
            if(partner == null) {
                matchRepository.save(new WaitingUser(uid,null, score, gameType,null,null));
                return new MatchResponseDto(null,null,null,null);
            }
            else {
                int myTurn = makeTurn();
                partner.setTurn((myTurn + 1) % 2);
                Long uid0, uid1;
                Long partnerUid = partner.getUid();

                if(myTurn == 0) {
                    uid0 = uid;
                    uid1 = partnerUid;
                }
                else {
                    uid0 = partnerUid;
                    uid1 = uid;
                }

                Room room = roomManager.createRoom();

                historyService.makeHistory(new History(room.getGameId(),uid0, uid1, loginService.getScore(uid0), loginService.getScore(uid1), Timestamp.valueOf(LocalDateTime.now())));

                room.setUIDs(new Long[]{uid0,uid1});

                partner.setGameID(room.getGameId());
                partner.setOpponentUid(uid);
                matchRepository.update(partner);

                return new MatchResponseDto(room.getGameId(), myTurn, loginService.getName(partnerUid), loginService.getScore(partnerUid));
            }
        }
    }

    private int makeTurn() {
        Random random = new Random();
        return random.nextInt(2);
    }
}
