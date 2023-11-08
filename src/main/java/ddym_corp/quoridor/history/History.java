package ddym_corp.quoridor.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.Column;
//import javax.persistence.ElementCollection;
//import javax.persistence.Embeddable;
//import javax.persistence.FetchType;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class History {

    private Long gameId;
    //
    private Long uid0;
    private Long uid1;
    // 게임 당시 score
    private Integer score0;
    private Integer score1;
    //
    private Timestamp stamp;
    //
    private String moves;
    // 누가 이겼는 지
    private Long winnerId;

    private IdScore idScore0;
    private IdScore idScore1;
    public History(Long gameId, Long uid0, Long uid1, Integer score0, Integer score1, Timestamp stamp) {
        this.gameId = gameId;
        this.uid0 = uid0;
        this.uid1 = uid1;
        this.score0 = score0;
        this.score1 = score1;
        this.stamp = stamp;
        this.moves = "";
        this.idScore0 = new IdScore(uid0, score0);
        this.idScore1 = new IdScore(uid1, score1);
    }

    @AllArgsConstructor
    @Getter @Setter
    public class IdScore{
        private Long uid;
        private Integer score;
    }
}