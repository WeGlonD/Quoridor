package ddym_corp.quoridor.match.service.background;

public interface BackgroundMatchLogic {
    void match();
    void join(QueueUser queueUser);
}
