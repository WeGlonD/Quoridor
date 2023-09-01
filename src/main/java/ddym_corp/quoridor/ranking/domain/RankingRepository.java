package ddym_corp.quoridor.ranking.domain;

import java.util.List;

public interface RankingRepository {
    //crud
    Long save(Long uid);
    Long update(Long uid, int preScore, int preGameCnt, int nxtScore, int nxtGameCnt);
    RankingUser find1st();
    List<Long> findUpward(Long uid, int size);
    List<Long> findDownward(Long uid, int size);
}
