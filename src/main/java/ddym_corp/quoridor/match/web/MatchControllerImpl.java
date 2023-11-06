package ddym_corp.quoridor.match.web;

import ddym_corp.quoridor.match.background.PreMatchedUser;
import ddym_corp.quoridor.match.foreground.service.MatchService;
import ddym_corp.quoridor.match.utils.MatchResponseDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static ddym_corp.quoridor.user.auth.web.uitls.SessionConst.USER_ID;
@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchControllerImpl implements MatchController{

    private final MatchService matchService;
    private final UserRepository userRepository;


    @GetMapping("/matched_users")
    public MatchResponseDto matchCheck(HttpServletRequest request) {

        // 세션으로부터 uid 받아오기
        Long uid = getUid(request);

        // 성공하면 matchResponseDto 실패하면 null 반환
        return matchService.check(uid);
    }

    /**
     * 대기열 취소할 때 혹시 매칭이 되어있으면 matchResponseDto 아니라면 null 반환
     * @param request
     * @return MatchResponseDto or null
     */
    @DeleteMapping("/matched_users")
    public MatchResponseDto escape(@Valid @RequestBody Integer gameType, HttpServletRequest request){

        // 세션으로부터 uid 받아오기
        Long uid = getUid(request);

        // 성공하면 matchResponseDto 실패하면 null 반환
        synchronized (PreMatchedUser.class) {
            MatchResponseDto dto = matchService.check(uid);
            if (dto == null) {
                matchService.exit(uid, gameType);
                return null;
            }
            return dto;
        }
    }
    @Override
    @PostMapping("/matched_users")
    public String matchStart(@RequestBody MatchDto matchDto, HttpServletRequest request) {

        // 세션으로부터 uid 받아오기
        Long uid = getUid(request);

        log.info("findPartner - findingUser: {}, GameType: {}", uid, matchDto.getGameType());

        // gameType 부분 수정해야함
        int gameType = matchDto.getGameType();

        // MatchService 대기열 참여
        matchService.join(uid, getScore(uid), gameType);

        return "OK"; // 성공처리
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
