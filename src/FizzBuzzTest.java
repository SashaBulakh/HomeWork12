import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzzTest {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        myProducer fizzbuzzProducer = new myProducer() {

            int n;
            boolean update = false;

            public boolean isUpdate() {
                return update;
            }

            @Override
            public void set(int n) {
                this.n = n;
                update = true;
            }

            @Override
            public void run() {
                while (true){
                    try {
                        if (update){
                            update = false;
                            if ((n % 3 == 0) && (n % 5 == 0)){
                                queue.put("fizzbuzz");
                            }
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        
        myProducer fizzProducer = new myProducer() {

            int n;
            boolean update = false;

            public boolean isUpdate() {
                return update;
            }

            @Override
            public void set(int n) {
                this.n = n;
                update = true;
            }

            @Override
            public void run() {
                while (true){
                    try {
                        if (update){
                            update = false;
                            if ((n % 3 == 0) && (n % 5 != 0)){
                                queue.put("fizz");
                            }
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        myProducer buzzProducer = new myProducer() {

            int n;
            boolean update = false;

            public boolean isUpdate() {
                return update;
            }

            @Override
            public void set(int n) {
                this.n = n;
                update = true;
            }

            @Override
            public void run() {
                while (true){
                    try {
                        if (update){
                            update = false;
                            if ((n % 5 == 0) &&(n % 3 != 0)){
                                queue.put("buzz");
                            }
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        myProducer numberProducer = new myProducer() {

            int n;
            boolean update = false;

            public boolean isUpdate() {
                return update;
            }

            @Override
            public void set(int n) {
                this.n = n;
                update = true;
            }

            @Override
            public void run() {
                while (true){
                    try {
                        if (update){
                            update = false;
                            if ((n % 3 != 0) && (n % 5 != 0)){
                                queue.put(String.valueOf(n));
                            }
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Runnable consumer = () -> {
            while (true){
                while (!queue.isEmpty()){
                    System.out.println(queue.poll());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };


        ExecutorService executor = Executors.newFixedThreadPool( 5);
        executor.execute(fizzbuzzProducer);
        executor.execute(numberProducer);
        executor.execute(fizzProducer);
        executor.execute(buzzProducer);
        executor.execute(consumer);


        for (int i = 1; i <= 15; i++) {
            fizzProducer.set(i);
            numberProducer.set(i);
            buzzProducer.set(i);
            fizzbuzzProducer.set(i);
            while (fizzProducer.isUpdate() || numberProducer.isUpdate() || buzzProducer.isUpdate()
                    || fizzbuzzProducer.isUpdate()){
                Thread.sleep(100);

            }
        }

    }
}

interface myProducer extends Runnable{
     void set(int n);
     boolean isUpdate();

}
