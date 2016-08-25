package shanren.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自己实现一个锁
 *
 * @author tanxianwen 2016年8月25日
 */
public class MyBlockingQueue {

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Object[] items = new Object[100];
    private int putIndex, takeIndex, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putIndex] = x;
            if (++putIndex == items.length) putIndex = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public boolean put(Object e, long timeout, TimeUnit unit) throws InterruptedException {

        long nanos = unit.toNanos(timeout);
        final Lock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                if (nanos <= 0) return false;
                nanos = notFull.awaitNanos(nanos);
            }
            items[putIndex] = e;
            if (++putIndex == items.length) putIndex = 0;
            ++count;
            notEmpty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeIndex];
            if (++takeIndex == items.length) takeIndex = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public Object take(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final Lock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                if (nanos <= 0) return null;
                nanos = notEmpty.awaitNanos(nanos);
            }
            Object x = items[takeIndex];
            if (++takeIndex == items.length) takeIndex = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public Object peek() {
        final Lock lock = this.lock;
        lock.lock();
        try {
            return items[takeIndex]; // null when queue is empty
        } finally {
            lock.unlock();
        }
    }
}
