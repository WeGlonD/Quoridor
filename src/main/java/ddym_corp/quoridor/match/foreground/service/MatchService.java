package ddym_corp.quoridor.match.foreground.service;

import ddym_corp.quoridor.match.utils.MatchResponseDto;

public interface MatchService {
    MatchResponseDto check(Long uid);
    void join(Long uid, Integer score, Integer gameType);

    void exit(Long uid, Integer gameType);
}
