package shanren;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class T {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // FutureTask<String> task = new FutureTask<String>(() -> {
        // return "da";
        // });
        // Thread.yield();
        // ///Thread.sleep(1000000);
        // //System.out.println(task.get());
        // // task.run();
        // // for (;;) {
        // // System.out.println(task.get());
        // // LockSupport.parkNanos(task, 1000000000L);
        // //
        // // }
        //
        // for (int i = 0, size = size(); i < size; i++) {
        // System.out.println(i);
        // }
        long LONG_MASK = 0xffffffffL;
        long a = 3;
        final long l = 3 & LONG_MASK;
        long diff = l - (8 & LONG_MASK);
        System.out.println("LONG_MASK "+LONG_MASK);
        System.out.println(diff);
        System.out.println((int) diff);
        System.out.println(-5L & LONG_MASK);
        System.out.println(Long.toBinaryString(((long) Integer.MAX_VALUE * Integer.MAX_VALUE) & LONG_MASK));
        System.out.println(Long.toBinaryString(((long) Integer.MAX_VALUE * Integer.MAX_VALUE)));
        System.out.println(Integer.toBinaryString(-5));

        System.out.println(diff >> 32);

    }


    private static int size() {
        System.out.println("size");
        return 10;
    }



}
