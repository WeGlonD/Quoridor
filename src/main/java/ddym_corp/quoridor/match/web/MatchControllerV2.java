package ddym_corp.quoridor.match.web;

import ddym_corp.quoridor.match.service.foreground.MatchService;
import ddym_corp.quoridor.match.utils.MatchResponseDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static ddym_corp.quoridor.auth.web.SessionConst.USER_ID;
@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchControllerV2 implements MatchController{

    private final MatchService matchService;
    private final UserRepository userRepository;


    @GetMapping("/matched_users")
    public MatchResponseDto matchCheck(HttpServletRequest request) {

        // 세션으로부터 uid 받아오기
        Long uid = getUid(request);

        // 성공하면 matchResponseDto 실패하면 null 반환
        return matchService.check(uid);
    }
    @Override
    @PostMapping("/matched_users")
    public MatchResponseDto matchStart(@RequestBody MatchDto matchDto, HttpServletRequest request) {

        // 세션으로부터 uid 받아오기
        Long uid = getUid(request);

        log.info("findPartner - findingUser: {}, GameType: {}", uid, matchDto.getGameType());

        // gameType 부분 수정해야함
        int gameType = matchDto.getGameType();
        // 수정 필요!!!!!

        // MatchService 대기열 참여
        matchService.join(uid, getScore(uid));

        return null; // 성공처리
    }

    /**
     * uid로 score를 userRepository로 부터 얻는 메소드
     * @param uid
     * @return score
     */
    private Integer getScore(Long uid) {
        Integer score = null;
        try {
            Optional<User> byuID = userRepository.findByuID(uid);
            User user = byuID.get();
            score = user.getScore();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return score;
    }

    /**
     * 세션에서 uid를 얻고 세션 만료시간을 30분으로 늘려주는 메소드
     * @param request
     * @return uid
     */
    private static Long getUid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);
        Long uid = (Long) session.getAttribute(USER_ID);
        return uid;
    }
}
