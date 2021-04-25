package lottery;

import java.util.Random;

public class Lottery {
    public void start() {
        Random random = new Random();
        int times = 0;
        for (int i = 0; i < 1000000; i++) {
            times++;
            int num = random.nextInt(1000);
            if (num == 25) {
                if (times > 5000) {
                    System.out.println(times);
                }
                times = 0;
            }
        }
    }
}
