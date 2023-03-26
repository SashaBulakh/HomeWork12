import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Methods {

    private static final BlockingQueue<String> QUEUE = new LinkedBlockingQueue<>();

    public static Runnable myProducer() {
        Runnable myProducer = new Runnable() {
            int value;

            @Override
            public void run() {
                value++;
                try {
                    QUEUE.put("notification " + value);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("value " + value);
            }
        };
        return myProducer;
    }

    public static Runnable myConsumer() {
        Runnable myConsumer = () -> {
            System.out.println("5 seconds passed");
            while (!QUEUE.isEmpty()) {
                System.out.println(QUEUE.poll());
            }
        };
        return myConsumer;
    }
}

