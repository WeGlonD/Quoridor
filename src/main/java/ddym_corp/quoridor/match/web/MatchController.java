package ddym_corp.quoridor.match.web;

import ddym_corp.quoridor.match.utils.MatchResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

public interface MatchController {
    String matchStart(MatchDto matchDto, HttpServletRequest request);
    MatchResponseDto matchCheck(HttpServletRequest request);
}
