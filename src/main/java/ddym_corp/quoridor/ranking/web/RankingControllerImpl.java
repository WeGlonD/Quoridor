package ddym_corp.quoridor.ranking.web;

import ddym_corp.quoridor.ranking.RankingUser;
import ddym_corp.quoridor.ranking.domain.service.RankingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ddym_corp.quoridor.user.auth.web.uitls.SessionConst.USER_ID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RankingControllerImpl implements RankingController {

    private final RankingService rankingService;

    @Override
    @GetMapping(value = "/ranking")
    public RankingResponse getRankingAroundMe(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);

        RankingResponse response = new RankingResponse();
        response.setRankingUserList(rankingService.find20AroundMe((Long) session.getAttribute(USER_ID)));
        response.setFirstElementRank(rankingService.findRank(response.getRankingUserList().get(0)));

        return response;
    }

    @Override
    @GetMapping(value = "/ranking/over")
    public RankingResponse getRankingOverThis(@RequestParam("uid") Long uid, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);

        RankingResponse response = new RankingResponse();
        List<RankingUser> upward = rankingService.findUpward(uid, 20);
        response.setRankingUserList(upward);
        if(!upward.isEmpty()) {
            response.setFirstElementRank(rankingService.findRank(upward.get(0)));
        }

        return response;
    }

    @Override
    @GetMapping(value = "/ranking/under")
    public RankingResponse getRankingUnderThis(@RequestParam Long uid, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);

        RankingResponse response = new RankingResponse();
        List<RankingUser> downward20 = rankingService.findDownward(uid, 20);
        response.setRankingUserList(downward20);
        if(!downward20.isEmpty()){
            response.setFirstElementRank(rankingService.findRank(downward20.get(0)));
        }

        return response;
    }
}
