package ddym_corp.quoridor.match.background.min3;

import ddym_corp.quoridor.match.background.PreMatchedUser;

public interface BackgroundMatchLogic3Min {
    void run() throws InterruptedException;
    void join(PreMatchedUser queueUser);

    void divide(Long uid);
}
