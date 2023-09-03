package ddym_corp.quoridor.ranking.domain.repository;

import ddym_corp.quoridor.ranking.RankingUser;

import java.util.List;

public interface RankingRepository {
    //crud
    RankingUser save(Long uid);
    RankingUser update(Long uid, int preScore, int nxtScore);
    RankingUser delete(Long uid);
    void clearAll();
    RankingUser findOne(Long uid);

    String getRank(RankingUser rankingUser);

    /**
     * 현재 랭킹 1위의 정보를 가져옴
     * @return RankingUser who is first rank
     */
    RankingUser find1st();

    /**
     * 해당 uid 유저보다 점수가 높은 유저들을 size 이하 갯수로 반환.
     * 기준 uid 유저는 포함 X
     * @param uid criterion USER_ID
     * @param size return size
     * @return
     */
    List<RankingUser> findUpward(Long uid, int size);

    /**
     * 해당 uid 유저와 그 보다 점수가 낮은 유저들이 포함된 리스트를 반환.
     * 총 리스트 size는 size + 1개.
     * @param uid
     * @param size
     * @return
     */
    List<RankingUser> findDownward(Long uid, int size, boolean inclusive);
}
