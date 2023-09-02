package ddym_corp.quoridor.ranking.domain.service;

import ddym_corp.quoridor.ranking.RankingUser;

import java.util.List;

public interface RankingService {
    RankingUser join(Long uid);
    RankingUser update(Long uid, int preScore, int nxtScore);
    String findRank(RankingUser rankingUser);
    /**
     * 주어진 유저와 그의 위로 10개 아래로 10개 랭킹 유저를 반환
     * @param uid criterion USER_ID
     * @return List of RankingUser sized 21 (or less)
     */
    List<RankingUser> find20AroundMe(Long uid);

    /**
     * 주어진 유저의 아래 size+1개 랭킹을 가져온다. (주어진 유저 포함)
     * uid가 음수면 처음부터 size+1개 가져온다.
     * @param uid
     * @return List of RankingUser sized by param 'size' (or less)
     */
    List<RankingUser> findDownward(Long uid, int size);

    /**
     * 주어진 유저의 위 size개 랭킹을 가져온다. (주어진 유저 제외)
     * @param uid
     * @return List of RankingUser sized by param 'size' (or less)
     */
    List<RankingUser> findUpward(Long uid, int size);
}
