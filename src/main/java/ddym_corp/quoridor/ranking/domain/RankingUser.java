package ddym_corp.quoridor.ranking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingUser {
    int score;
    int gameCnt;
    Long uid;
}
