package ddym_corp.quoridor.ranking.domain.repository;

import ddym_corp.quoridor.ranking.RankingUser;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
public class MemoryRankingRepository implements RankingRepository {

    private final TreeSet<RankingUser> rankingBST = new TreeSet<>(new Comparator<RankingUser>() {
        @Override
        public int compare(RankingUser o1, RankingUser o2) {
            if (o1.getScore() != o2.getScore()) return o2.getScore() - o1.getScore();
            return o1.getUid() - o2.getUid() > 0 ? 1 : -1;
        }
    });
    private final HashMap<Long, RankingUser> rankingHash = new HashMap<>();

    @Override
    public RankingUser save(Long uid) {
        RankingUser rankingUser = new RankingUser(0, uid);
        rankingBST.add(rankingUser);
        rankingHash.put(uid, rankingUser);

        return rankingUser;
    }

    @Override
    public RankingUser update(Long uid, int preScore, int nxtScore) {
        RankingUser preRankingUser = new RankingUser(preScore, uid);
        rankingBST.remove(preRankingUser);
        RankingUser nxtRankingUser = new RankingUser(nxtScore, uid);
        rankingBST.add(nxtRankingUser);

        rankingHash.replace(uid, nxtRankingUser);

        return nxtRankingUser;
    }

    @Override
    public RankingUser findOne(Long uid) {
        return rankingHash.get(uid);
    }

    @Override
    public String getRank(RankingUser rankingUser) {
        return rankingBST.headSet(rankingUser).size() + "/" + rankingHash.size();
    }

    @Override
    public RankingUser find1st() {
        return rankingBST.first();
    }

    @Override
    public List<RankingUser> findUpward(Long uid, int size) {
        RankingUser currUser = rankingHash.get(uid);
        int idx = rankingBST.headSet(currUser).size();
        int upLimit = Math.max(idx - size, 0);

        return rankingBST.headSet(currUser).stream().toList().subList(upLimit, idx);
    }

    @Override
    public List<RankingUser> findDownward(Long uid, int size, boolean inclusive) {
        RankingUser currUser = rankingHash.get(uid);
        int s = rankingBST.tailSet(currUser, inclusive).size();
        if (inclusive) size++;
        int downLimit = Math.min(size, s);

        return rankingBST.tailSet(currUser, inclusive).stream().toList().subList(0, downLimit);
    }

    @PostConstruct
    void init() {
        RankingUser jaemo = new RankingUser(0, 1L);
        RankingUser dw = new RankingUser(0, 2L);

        rankingBST.add(jaemo);
        rankingBST.add(dw);

        rankingHash.put(jaemo.getUid(), jaemo);
        rankingHash.put(dw.getUid(), dw);
    }
}
