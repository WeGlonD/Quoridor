package ddym_corp.quoridor.game.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class MoveMessage {
    private Integer type;
    private Long remainTime;
    private Integer row;
    private Integer col;
}
