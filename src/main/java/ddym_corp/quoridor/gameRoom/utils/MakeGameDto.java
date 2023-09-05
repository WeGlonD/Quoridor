package ddym_corp.quoridor.gameRoom.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MakeGameDto {
    private Long uID0;
    private Long uID1;

    private Integer gameType;
}
