package ddym_corp.quoridor.auth.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser {

    private Long uid;
    private String gameId;
    private Integer turn;

    public SessionUser(Long uid) {
        this.uid = uid;
    }
}
