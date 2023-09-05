package ddym_corp.quoridor.match.service.background;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreMatchedUser {
    private Long uid;
    private Integer score;
}
