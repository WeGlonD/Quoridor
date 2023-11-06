package ddym_corp.quoridor.match.background.min10;

import ddym_corp.quoridor.match.background.PreMatchedUser;

public interface BackgroundMatchLogic10Min {
    void run() throws InterruptedException;
    void join(PreMatchedUser queueUser);

    void divide(Long uid);
}
