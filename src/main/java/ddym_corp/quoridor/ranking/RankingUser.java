package ddym_corp.quoridor.ranking;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.ZSetOperations;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RankingUser {
    private int score;
//    private int totalGames;
//    private int winGames;
    private Long uid;

    public static RankingUser convertToRankingUser(ZSetOperations.TypedTuple<Long> typedTuple) {
        return new RankingUser((BigDecimal.valueOf(typedTuple.getScore())).intValue(), typedTuple.getValue());
    }
}
