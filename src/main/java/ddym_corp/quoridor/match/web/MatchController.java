package ddym_corp.quoridor.match.web;

import ddym_corp.quoridor.match.utils.MatchResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

public interface MatchController {
    MatchResponseDto matchStart(MatchDto matchDto, HttpServletRequest request);

    @NoArgsConstructor
    @Getter @Setter
    static class MatchDto {
        private int gameType; //ex) 1vs1 10분 = 0번
    }
}
