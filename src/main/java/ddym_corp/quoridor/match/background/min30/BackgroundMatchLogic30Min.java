package ddym_corp.quoridor.match.background.min30;

import ddym_corp.quoridor.match.background.PreMatchedUser;

public interface BackgroundMatchLogic30Min {
    void run() throws InterruptedException;
    void join(PreMatchedUser queueUser);

    void divide(Long uid);
}
