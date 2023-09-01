package ddym_corp.quoridor.ranking.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryRankingRepository implements RankingRepository {

    private final TreeSet<RankingUser> rankingBST = new TreeSet<>();
    private final HashMap<Long, RankingUser> rankingHash = new HashMap<>();

    @Override
    public Long save(Long uid) {
        RankingUser rankingUser = new RankingUser(0, 0, uid);
        rankingBST.add(rankingUser);
        rankingHash.put(uid, rankingUser);

        return (long)rankingBST.headSet(rankingUser).size() + 1;
    }

    @Override
    public Long update(Long uid, int preScore, int preGameCnt, int nxtScore, int nxtGameCnt) {
        RankingUser preRankingUser = new RankingUser(preScore, preGameCnt, uid);
        rankingBST.remove(preRankingUser);
        RankingUser nxtRankingUser = new RankingUser(nxtScore, nxtGameCnt, uid);
        rankingBST.add(nxtRankingUser);

        rankingHash.replace(uid, nxtRankingUser);

        return (long)rankingBST.headSet(nxtRankingUser).size() + 1;
    }

    @Override
    public RankingUser find1st() {
        return rankingBST.first();
    }

    @Override
    public List<Long> findUpward(Long uid, int size) {
        RankingUser currUser = rankingHash.get(uid);
//        Collections.reverse(rankingBST.headSet(currUser).stream().limit(10))
        //이어하기



        return null;
    }

    @Override
    public List<Long> findDownward(Long uid, int size) {
        return null;
    }
}
