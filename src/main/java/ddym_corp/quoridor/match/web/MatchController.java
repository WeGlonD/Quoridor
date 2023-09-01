package ddym_corp.quoridor.match.web;

import ddym_corp.quoridor.match.utils.MatchResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;

public interface MatchController {
    MatchResponseDto findPartner(MatchDto matchDto, HttpServletRequest request);

    @NoArgsConstructor
    @Data
    static class MatchDto {
        private int gameType; //ex) 1vs1 10분 = 0번
    }
}
