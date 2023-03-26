import java.util.concurrent.*;


public class CounterTest {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(Methods.myConsumer(), 5, 5, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(Methods.myProducer(), 1, 1, TimeUnit.SECONDS);

        Thread.sleep(20000);
        executor.shutdown();
    }
}
