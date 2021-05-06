package lottery;

import java.io.IOException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        Collector collector = new Collector();
        Analyser analyser = new Analyser();
        Lottery lottery = new Lottery();
        // analyser.start();
        // collector.start();
        lottery.start();
    }
}
