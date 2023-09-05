package ddym_corp.quoridor.match.service.background;

public interface BackgroundMatchLogic {
    void run() throws InterruptedException;
    void join(PreMatchedUser queueUser);
}
