package ddym_corp.quoridor.match.foreground;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchedUser {
    private Long uid;
//    private Boolean turn; // True -> first, False -> second
    private Integer turn; // True -> first, False -> second
    private Long gameId;
}
