package ddym_corp.quoridor.match.foreground.service;

import ddym_corp.quoridor.gameRoom.repository.GameRoomRepository;
import ddym_corp.quoridor.gameRoom.service.GameRoom;
import ddym_corp.quoridor.match.background.BackgroundMatchLogic;
import ddym_corp.quoridor.match.background.PreMatchedUser;
import ddym_corp.quoridor.match.foreground.MatchedUser;
import ddym_corp.quoridor.match.foreground.repository.MatchRepository;
import ddym_corp.quoridor.match.utils.MatchResponseDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{
    private final MatchRepository matchRepository;
    private final GameRoomRepository gameRepository;
    private final UserRepository userRepository;
    private final BackgroundMatchLogic backgroundMatchLogic;
    @Override
    public MatchResponseDto check(Long uid){
        Optional<MatchedUser> optionalMatchedUser = matchRepository.findByUid(uid);
        if (optionalMatchedUser.isEmpty()){
            log.info("not in store");
            return null;
        }
        // gameId로 상대방 uid 찾기
        MatchedUser me = optionalMatchedUser.get();
        GameRoom room = gameRepository.findByGameId(me.getGameId());
        Optional<Long> partnerIdOpt = List.of(room.getUIDs()).stream().filter(id -> id != uid).findAny();
        Long partnerId = partnerIdOpt.get();

        // 상대방  uid로 score와 name 찾기
        Optional<User> partnerOpt = userRepository.findByuID(partnerId);
        User partner = partnerOpt.get();

        // store에서 삭제
        matchRepository.delete(uid);

        return new MatchResponseDto(room.getGameId(), me.getTurn(), partner.getName(), partner.getScore());
    }
    @Override
    public void join(Long uid, Integer score){
        backgroundMatchLogic.join(new PreMatchedUser(uid, score));
    }

}
