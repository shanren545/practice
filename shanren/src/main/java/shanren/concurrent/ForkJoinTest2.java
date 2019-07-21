package shanren.concurrent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ForkJoinTest2 {
    int[] nums = new int[1000_0000];
    int CI = 1000;// 计算多少次
    Random r = new Random();

    public ForkJoinTest2() {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
    }

    public static void main(String[] args) throws IOException {
        Arrays.stream(new int[3]).sum();// 初始化sum内部排除初始化时间
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1");
        ForkJoinTest2 t = new ForkJoinTest2();
        t.cc();
    }



    public void cc() throws IOException {
        // ForkJoinPool pool = new ForkJoinPool();
        ForkJoinPool pool = ForkJoinPool.commonPool();

        AddTask task = new AddTask(0, nums.length);
        long start = System.currentTimeMillis();
        // for (int j = 0; j < CI; j++) {
        // pool.execute(task);
        // }
        //task.fork();
        pool.submit(task);
        long s = task.join();
        long end = System.currentTimeMillis();
       

        System.out.println("aa|" + (end - start) + "ms|" + s);
        System.in.read();
    }

    class AddTask extends RecursiveTask<Long> {
        private static final long serialVersionUID = 1L;
        int start;
        int end;

        public AddTask(int s, int e) {
            this.start = s;
            this.end = e;
        }

        @Override
        protected Long compute() {
            if (end - start <= 50000) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return sum;
            }
            int m = start + (end - start) / 2;

            AddTask sub1 = new AddTask(start, m);
            AddTask sub2 = new AddTask(m, end);
            //sub1.fork();
            // sub2.fork();

            //AddTask.invokeAll(sub1,sub2);
            ForkJoinPool.commonPool().submit(sub1);
            ForkJoinPool.commonPool().submit(sub2);
            final Long rt2 = sub2.join();
            final Long rt1 = sub1.join();
            // final Long rt2 = sub2.join();
            return rt1 + rt2;
        }

    }
}
