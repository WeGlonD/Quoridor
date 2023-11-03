package ddym_corp.quoridor.match.background;

public interface BackgroundMatchLogic {
    void run() throws InterruptedException;
    void join(PreMatchedUser queueUser);

    void divide(Long uid);
}
