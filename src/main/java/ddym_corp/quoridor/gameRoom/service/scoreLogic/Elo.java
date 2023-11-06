package ddym_corp.quoridor.gameRoom.service.scoreLogic;

import ddym_corp.quoridor.gameRoom.service.GameResultType;

public class Elo {
    private static final double ratio = 10.0;
    private static final double diff = 400.0;
    private static final double K = 20.0;

    public static final int INIT_SCORE = 800;

    public static double calcW(int myRating, int opponentRating) {
        return 1/(Math.pow(ratio, (opponentRating - myRating) / diff) + 1);
    }

    public static int calcRating(int myRating, int opponentRating, GameResultType gameResult) {
        int ret = (int)(myRating + K * (gameResult.ordinal()/2.0 - calcW(myRating, opponentRating)));
        if(ret < 0) ret = 0;
        return ret;
    }
}
