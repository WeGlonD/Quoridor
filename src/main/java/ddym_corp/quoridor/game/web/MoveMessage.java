package ddym_corp.quoridor.game.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveMessage {
    private Integer type;
    private Long remainTime;
    private Integer row;
    private Integer col;
}
