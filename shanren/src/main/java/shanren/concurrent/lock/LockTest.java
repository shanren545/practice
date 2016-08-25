package shanren.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockTest {

    static final int SHARED_SHIFT = 16;
    static final int SHARED_UNIT = (1 << SHARED_SHIFT);

    public static void main(String[] args) {

        System.out.println(SHARED_SHIFT);
        System.out.println(SHARED_UNIT);
        Lock l = new ReentrantLock();
        l.lock();
        long ct = 0;
        try {
            l.lock();
            // access the resource protected by this lock
        } finally {
            l.unlock();
        }
    }


    public void demoTryLock() {
        Lock lock = null;
        if (lock.tryLock()) {
            try {
                // manipulate protected state
            } finally {
                lock.unlock();
            }
        } else {
            // perform alternative actions
        }
    }

   

    
    
    
    
    
    
    
    
    class CachedData {
        Object data;
        volatile boolean cacheValid;
        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        void processCachedData() {
          rwl.readLock().lock();
          if (!cacheValid) {
            // Must release read lock before acquiring write lock
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {
              // Recheck state because another thread might have
              // acquired write lock and changed state before we did.
              if (!cacheValid) {
                //data = ...
                cacheValid = true;
              }
              // Downgrade by acquiring read lock before releasing write lock
              rwl.readLock().lock();
            } finally {
              rwl.writeLock().unlock(); // Unlock write, still hold read
            }
          }

          try {
            //use(data);
          } finally {
            rwl.readLock().unlock();
          }
        }
    }
}



