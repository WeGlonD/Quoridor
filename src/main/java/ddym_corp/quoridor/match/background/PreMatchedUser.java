package ddym_corp.quoridor.match.background;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreMatchedUser {
    private Long uid;
    private Integer score;
}
