package ddym_corp.quoridor.match.service;

import ddym_corp.quoridor.match.utils.MatchResponseDto;

public interface MatchService {
    MatchResponseDto findOpponent(Long uID, int gameType);
}
