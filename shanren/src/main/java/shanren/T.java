package shanren;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import com.google.common.collect.Streams;

public class T {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // int corePoolSize,
        // int maximumPoolSize,
        // long keepAliveTime,
        // TimeUnit unit,
        // BlockingQueue<Runnable> workQueue

        // ThreadPoolExecutor e = new ThreadPoolExecutor(8, 8, 1, TimeUnit.HOURS, new
        // ArrayBlockingQueue<>(8));
        // for (int i = 0; i < 8; i++) {
        // e.submit((Runnable) () -> {
        // long j = 0;
        // while (true)
        // j++;
        // });
        // }
        //
        // FutureTask<String> task = new FutureTask<String>(() -> {
        // return "da";
        // });
        // Thread.yield();
        // /// Thread.sleep(1000000);
        // // System.out.println(task.get());
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

        fanxing();


        // List<String>[] lsa = new List<String>[10]; // Not really allowed.
        // Object o = lsa;
        // Object[] oa = (Object[]) o;
        // List<Integer> li = new ArrayList<Integer>();
        // li.add(new Integer(3));
        // oa[1] = li; // Unsound, but passes run time store check
        // String s = lsa[1].get(0); // Run-time error ClassCastException.


        List<?>[] lsa = new List<?>[10]; // OK, array of unbounded wildcard type.
        Object o = lsa;
        Object[] oa = (Object[]) o;
        List<Integer> li = new ArrayList<Integer>();
        li.add(new Integer(3));
        oa[1] = li; // Correct.
        Integer i = (Integer) lsa[1].get(0); // OK
    }


    private static int size() {
        System.out.println("size");
        return 10;
    }

    public static void fanxing() {
        ArrayList l2 = new ArrayList<>();
        ArrayList<Integer> l3 = new ArrayList<>();
        final Type genericSuperclass = l2.getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        System.out.println(type);
        System.out.println(l3.getClass().getTypeParameters()[0].getTypeName());
        System.out.println(l2.getClass().hashCode());
        System.out.println(l3.getClass().hashCode());

        System.out.println(l2 instanceof ArrayList);

        l2 = l3;

        Arrays.stream(T.class.getMethods()).forEach(System.out::println);

        final String unsignedString = Integer.toUnsignedString(-1);
        Integer i = Integer.parseUnsignedInt(unsignedString);
        System.out.println(unsignedString);
        System.out.println(i);
    }


    public static class T1 {
        private Object value;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
    public static class T2<T> extends T1 {

        // @Override
        public void setValue(Date value) {}

        @Override
        public Date getValue() {
            return null;
        }
    }

}
