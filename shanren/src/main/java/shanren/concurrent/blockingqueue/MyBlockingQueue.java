package shanren.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyBlockingQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        Thread t1 = new Thread(p);
        t1.start();
        Thread t2 = new Thread(c1);
        t2.start();
        Thread t3 = new Thread(c2);
        t3.start();

        Thread.sleep(10000L);
        t1.interrupt();
        t2.interrupt();
         t3.interrupt();
    }
}



class Producer implements Runnable {
    private final BlockingQueue queue;

    private int i = 0;

    Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                queue.put(produce());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    Object produce() {
        return String.valueOf(i++);
    }
}


class Consumer implements Runnable {
    private final BlockingQueue queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    void consume(Object x) {
        System.out.println(x);
    }
}

