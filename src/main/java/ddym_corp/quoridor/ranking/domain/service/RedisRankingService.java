package ddym_corp.quoridor.ranking.domain.service;

import ddym_corp.quoridor.ranking.RankingUser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisRankingService implements RankingService {
    private final String key = "ranking";

    private final RedisTemplate<String,Long> redisTemplate;

    @Override
    @Transactional
    public RankingUser join(Long uid) {
        RankingUser rankingUser = new RankingUser(0,uid);
        redisTemplate.opsForZSet().add(key, uid, 0);
        return rankingUser;
    }

    @Override
    @Transactional
    public RankingUser update(Long uid, int preScore, int nxtScore) {
        redisTemplate.opsForZSet().remove(key, uid);
        redisTemplate.opsForZSet().add(key, uid, nxtScore);

        return new RankingUser(nxtScore, uid);
    }

    @Override
    public String findRank(RankingUser rankingUser) {
        return (findIndex(rankingUser.getUid())+1)+"/"+redisTemplate.opsForZSet().size(key);
    }

    public Long findIndex(Long uid) {
        return redisTemplate.opsForZSet().reverseRank(key, uid);
    }

    @Override
    public List<RankingUser> find20AroundMe(Long uid) {
        //순위 높은 것들
        List<RankingUser> above = findUpward(uid, 10);

        //순위 낮은 것들
        List<RankingUser> below = findDownward(uid, 10);

        if(above == null) {
            return below;
        }

        above.addAll(below);
        return above;
    }

    @Override
    public List<RankingUser> findDownward(Long uid, int size) {
        //해당 uid의 인덱스
        Long idx = findIndex(uid);
        ZSetOperations<String, Long> zSetOperations = redisTemplate.opsForZSet();

        Set<ZSetOperations.TypedTuple<Long>> typedTuples = zSetOperations.reverseRangeWithScores(key, idx, idx + 10);
        List<RankingUser> below = typedTuples.stream().map(RankingUser::convertToRankingUser).toList();

        return below;
    }

    @Override
    public List<RankingUser> findUpward(Long uid, int size) {
        //해당 uid의 인덱스
        Long idx = findIndex(uid);
        ZSetOperations<String, Long> zSetOperations = redisTemplate.opsForZSet();

        List<RankingUser> above = null;
        if(idx - 1 > 0) {
            Set<ZSetOperations.TypedTuple<Long>> typedTuples = zSetOperations.reverseRangeWithScores(key, idx - 10, idx - 1);
            above = typedTuples.stream().map(RankingUser::convertToRankingUser).toList();
        }
        return above;
    }

    @PostConstruct
    void init() {
        join(1L);
        join(2L);
    }
}
