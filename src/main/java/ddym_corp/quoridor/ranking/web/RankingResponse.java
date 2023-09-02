package ddym_corp.quoridor.ranking.web;

import ddym_corp.quoridor.ranking.RankingUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingResponse {
    private String firstElementRank;
    private List<RankingUser> rankingUserList;
}
