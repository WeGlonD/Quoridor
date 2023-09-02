package ddym_corp.quoridor.ranking.web;

import ddym_corp.quoridor.ranking.RankingUser;

import jakarta.servlet.http.HttpServletRequest;

public interface RankingController {
    RankingResponse getRankingAroundMe(HttpServletRequest request);
    RankingResponse getRankingOverThis(Long uid, HttpServletRequest request);
    RankingResponse getRankingUnderThis(Long uid, HttpServletRequest request);

}
