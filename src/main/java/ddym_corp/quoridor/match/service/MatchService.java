package ddym_corp.quoridor.match.service;

import ddym_corp.quoridor.match.utils.MatchResponseDto;

public interface MatchService {
    MatchResponseDto selectPartner(Long uID, int gameType);
}
