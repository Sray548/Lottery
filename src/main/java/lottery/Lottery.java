package lottery;

import java.util.Random;

public class Lottery {
    public void start() {
        Random random = new Random();
        int times = 0;
        for (int i = 0; i < 10000000; i++) {
            times++;
            int num = random.nextInt(1000);
            if (num < 6) {
                if (times > 1262) {
                    System.out.println(times);
                }
                times = 0;
            }
        }
    }
}
