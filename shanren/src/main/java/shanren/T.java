package shanren;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class T {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> task = new FutureTask<String>(() -> {
            return "da";
        });
        Thread.yield();
        ///Thread.sleep(1000000);
        //System.out.println(task.get());
        // task.run();
        // for (;;) {
        // System.out.println(task.get());
        // LockSupport.parkNanos(task, 1000000000L);
        //
        // }

        for (int i = 0, size = size(); i < size; i++) {
            System.out.println(i);
        }
    }


    private static int size() {
        System.out.println("size");
        return 10;
    }



}
