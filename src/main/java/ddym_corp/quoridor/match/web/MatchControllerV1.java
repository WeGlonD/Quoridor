package ddym_corp.quoridor.match.web;

import ddym_corp.quoridor.match.service.MatchService;
import ddym_corp.quoridor.match.utils.MatchResponseDto;
import ddym_corp.quoridor.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import static ddym_corp.quoridor.auth.web.SessionConst.USER_ID;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@RestController
public class MatchControllerV1 implements MatchController {

    private final MatchService matchService;
    private final UserRepository userRepository;
    @Override
    @PostMapping("/match")
    public MatchResponseDto findPartner(@Valid @RequestBody MatchDto matchDto, HttpServletRequest request) {

        // 세션으로부터 uid 받아오기
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);
        Long uid = (Long) session.getAttribute(USER_ID);

        log.info("findPartner - findingUser: {}, GameType: {}", uid, matchDto.getGameType());

        int gameType = matchDto.getGameType();

        MatchResponseDto matchResponseDto = matchService.findOpponent(uid, gameType);
        if (matchResponseDto.getGameId() == null){
            log.info("UID:{} GameType:{} 매칭중", uid, matchDto.getGameType());
            return matchResponseDto;
        }

        log.info("UID:{} GameType:{} 매칭완료 GameID:{}", uid, matchDto.getGameType(),matchResponseDto.getGameId());
        return matchResponseDto;
    }
}
