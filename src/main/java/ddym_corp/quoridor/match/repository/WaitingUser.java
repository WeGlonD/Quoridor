package ddym_corp.quoridor.match.repository;

import lombok.*;

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
