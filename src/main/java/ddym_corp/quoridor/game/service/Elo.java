package ddym_corp.quoridor.game.service;

public class Elo {
    private static final double ratio = 10.0;
    private static final double diff = 400.0;
    private static final double K = 20.0;

    public static double calcW(int myRating, int opponentRating) {
        return 1/(Math.pow(ratio, (opponentRating - myRating) / diff) + 1);
    }

    public static int calcRating(int myRating, int opponentRating, GameResultType gameResult) {
        return (int)(myRating + K * (gameResult.ordinal()/2.0 - calcW(myRating, opponentRating)));
    }
}
