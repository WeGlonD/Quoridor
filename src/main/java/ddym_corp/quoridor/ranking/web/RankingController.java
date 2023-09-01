package ddym_corp.quoridor.ranking.web;

import ddym_corp.quoridor.ranking.domain.RankingUser;

import jakarta.servlet.http.HttpServletRequest;

public interface RankingController {
    RankingUser getMyRank(HttpServletRequest request);

}
