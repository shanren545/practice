package shanren.concurrent.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自己实现一个锁
 *
 * @author tanxianwen 2016年8月25日
 */
public class MyLock {

    private AtomicInteger state = new AtomicInteger(0);
    private Object lk = new Object();
    private volatile Thread owner;

    public void lock() throws InterruptedException {
        while (!state.compareAndSet(0, 1)) {
            lk.wait();
            // LockSupport.park();
        }
        owner = Thread.currentThread();
    }

    public void unlock() {
        if (Thread.currentThread() != owner) {
            throw new IllegalStateException("current thread have not hold the lock, can not unlock");
        }
        owner = null; // 先做清理工作，最后设置状态
        state.set(0);// unlock state
        lk.notify();
    }



}
