package shanren.concurrent.blockingqueue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;

public class LinkedTransferQueueTest {

    // public static void main(String[] args) {
    // LinkedTransferQueue<Integer> q = new LinkedTransferQueue<Integer>();
    // q.put(1);
    // q.put(2);
    // q.put(3);
    // q.put(4);
    //
    // }

    // private static boolean bChanged;
    //
    // public static void main(String[] args) throws InterruptedException {
    // new Thread() {
    //
    // @Override
    // public void run() {
    // for (;;) {
    // if (bChanged == !bChanged) {
    // System.out.println("!=");
    // System.exit(0);
    // }
    // }
    // }
    // }.start();
    // Thread.sleep(1);
    // new Thread() {
    //
    // @Override
    // public void run() {
    // for (;;) {
    // bChanged = !bChanged;
    // }
    // }
    // }.start();
    // }

    public static void main(String[] args) throws InterruptedException {
        // Exchanger<String> ex = new Exchanger<>();
        // ex.exchange("dd");
        // SynchronousQueue<String> d = new SynchronousQueue<String>();
        // d.put("d");

        // final CountDownLatch cd = new CountDownLatch(1);
        // new Thread(()->{try {
        // cd.await();
        // System.out.println("111");
        // Thread.sleep(10000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }}).start();;
        // new Thread(()->{try {
        // cd.await();
        // System.out.println("222");
        // Thread.sleep(10000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }}).start();;
        // Thread.sleep(1000);
        // cd.countDown();
        //
        final CyclicBarrier cr = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                try {
                    cr.await();
                } catch (BrokenBarrierException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("111");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();;
        new Thread(() -> {
            try {
                try {
                    cr.await();
                } catch (BrokenBarrierException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("222");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();;
        Thread.sleep(1000);
    }

}
