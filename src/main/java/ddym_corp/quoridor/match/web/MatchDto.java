package ddym_corp.quoridor.match.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class MatchDto {
    private int gameType; //ex) 1vs1 10분 = 0번
}
