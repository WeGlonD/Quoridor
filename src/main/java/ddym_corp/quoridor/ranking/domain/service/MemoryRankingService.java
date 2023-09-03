package ddym_corp.quoridor.ranking.domain.service;

import ddym_corp.quoridor.ranking.RankingUser;
import ddym_corp.quoridor.ranking.domain.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MemoryRankingService implements RankingService {
    private final RankingRepository rankingRepository;

    @Override
    public RankingUser join(Long uid) {
        return rankingRepository.save(uid);
    }

    @Override
    public RankingUser update(Long uid, int preScore, int nxtScore) {
        return rankingRepository.update(uid, preScore, nxtScore);
    }

    @Override
    public RankingUser signOut(Long uid) {
        return rankingRepository.delete(uid);
    }

    @Override
    public void removeAll() {
        rankingRepository.clearAll();
    }

    @Override
    public String findRank(RankingUser rankingUser) {
        return rankingRepository.getRank(rankingUser);
    }

    @Override
    public List<RankingUser> find20AroundMe(Long uid) {
        List<RankingUser> upward = rankingRepository.findUpward(uid, 10);
        log.info("upward user{} : {}", uid, upward);
        upward.addAll(rankingRepository.findDownward(uid, 10, true));
        log.info("up&down user{} : {}", uid, upward);

        return upward;
    }

    @Override
    public List<RankingUser> findDownward(Long uid, int size) {
        if (uid < 0) {
            uid = rankingRepository.find1st().getUid();
            return rankingRepository.findDownward(uid, size, true);
        }

        return rankingRepository.findDownward(uid, size, true);
    }

    @Override
    public List<RankingUser> findUpward(Long uid, int size) {
        return rankingRepository.findUpward(uid, size);
    }
}
