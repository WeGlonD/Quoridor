package ddym_corp.quoridor.match.service.foreground;

import ddym_corp.quoridor.game.repository.GameRepository;
import ddym_corp.quoridor.game.service.Room;
import ddym_corp.quoridor.match.service.background.BackgroundMatchLogic;
import ddym_corp.quoridor.match.service.background.PreMatchedUser;
import ddym_corp.quoridor.match.utils.MatchResponseDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final BackgroundMatchLogic backgroundMatchLogic;

    public MatchResponseDto check(Long uid){
        Optional<MatchedUser> optionalMatchedUser = matchRepository.findByUid(uid);
        if (optionalMatchedUser.isEmpty()){
            return null;
        }
        // gameId로 상대방 uid 찾기
        MatchedUser me = optionalMatchedUser.get();
        Room room = gameRepository.findByGameId(me.getGameId());
        Optional<Long> partnerIdOpt = List.of(room.getUIDs()).stream().filter(id -> id != uid).findAny();
        Long partnerId = partnerIdOpt.get();

        // 상대방  uid로 score와 name 찾기
        Optional<User> partnerOpt = userRepository.findByuID(partnerId);
        User partner = partnerOpt.get();

        // store에서 삭제
        matchRepository.delete(uid);

        return new MatchResponseDto(room.getGameId(), me.getTurn(), partner.getName(), partner.getScore());
    }

    public void join(Long uid, Integer score){
        backgroundMatchLogic.join(new PreMatchedUser(uid, score));
    }

}
