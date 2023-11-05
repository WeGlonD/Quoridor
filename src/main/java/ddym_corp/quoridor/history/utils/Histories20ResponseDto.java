package ddym_corp.quoridor.history.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Histories20ResponseDto {
    Long gameId;
    Boolean win; //True -> win, False -> lose
    String opponentName;
    Integer opponentScore;
    String opponentProfileImage;
}
