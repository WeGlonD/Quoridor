package ddym_corp.quoridor.match.service.background;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class SlideBackgroundMatchLogic implements BackgroundMatchLogic{

    LinkedList<QueueUser> store = new LinkedList<>();

    @Override
    public void match() {

    }

    @Override
    public void join(QueueUser queueUser) {
        store.add(queueUser);
    }

    private class MyThread implements Runnable{
        @Override
        public void run() {
            QueueUser first = store.getFirst();
            ArrayList<QueueUser> users = (ArrayList<QueueUser>) store.subList(1, 10);
            Integer firstScore = first.getScore();
            int i = 1;
            int idx = 1;
            int minGap = 9999999;
            for (QueueUser user : users) {
                int gap = Math.abs(user.getScore()-firstScore);
                if (gap < minGap){
                    minGap=gap;
                    idx=i;
                }
                i++;
            }

        }
    }
}