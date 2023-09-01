package ddym_corp.quoridor.match.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDto {
    private Long gameId;
    private Integer turn;
    private String opponentName;
    private Integer opponentScore;
}
