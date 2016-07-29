package shanren;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class Tester {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void testFutureTask() throws InterruptedException, ExecutionException {
        FutureTask<String> task = new FutureTask<String>(() -> {
            return "da";
        });
        // Thread.yield();
        /// Thread.sleep(1000000);
        task.run();
        System.out.println(task.get());
        for (;;) {
            System.out.println(task.get());
            LockSupport.parkNanos(task, 1000000000L);
        }
    }

}
