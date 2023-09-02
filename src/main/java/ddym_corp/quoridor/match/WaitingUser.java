package ddym_corp.quoridor.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WaitingUser {
    private Long uid;
    private Long opponentUid;
    private int score;
    private int gameType;
    private Long gameID;
    private Integer turn;
}
