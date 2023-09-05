package ddym_corp.quoridor.gameRoom.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveMessage {
    private Integer type;
    private Long remainTime;
    private Integer row;
    private Integer col;
}
