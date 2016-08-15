package shanren.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;

/**
 * thread pool test
 *
 * @author tanxianwen 2016年7月29日
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
            RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("afterExecute at:" + DateTime.now().toLocalDateTime() + " " + t);
    }

    @Override
    protected void terminated() {
        System.out.println("terminated at:" + DateTime.now().toLocalDateTime());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("beforeExecute at:" + DateTime.now().toLocalDateTime() + " " + t);
    }


    public static void main(String[] args) throws InterruptedException {
        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(2, 3, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1));

        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());

        // 测试通过jconsole查看各线程等待状态
        System.out.println(executor.getActiveCount());
        System.out.println(executor.getCompletedTaskCount());
        System.out.println(executor.getTaskCount());
        Thread.sleep(100000);
        executor.shutdownNow();
    }

    public static class MyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(100000);
            return Thread.currentThread().getName();
        }
    }

}
