package shanren.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntStreamTest {

    public static void main(String[] args) {
        testTheStreamImpl();
    }

    public static void test0() {
        final IntPredicate predicate = i -> i > 5;
        IntStream s = IntStream.range(1, 10).filter(predicate).filter(i -> i > 6);
        // stream每次都是返回一个新的
        // stream只能使用一次
        // stream是不可改的
        // s.filter(i -> i > 6);
        // s.filter(i -> i > 7);
        s.peek(System.out::println).forEach(System.out::println);;
        // s.forEach(System.out::println);

        Stream
                .of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
    }


    public static void testTheStreamImpl() {
        List<User> list = new ArrayList<>();
        list.add(new User(2));
        list.add(new User(34));
        list.add(new User(346));
        list.add(new User(654));
        list.add(new User(4));
        list.add(new User(98));
        list.add(new User(78));
        list.add(new User(34));
        list.add(new User(452));
        list.add(new User(255));
        list.add(new User(25664));
        list.add(new User(57572));
        list.add(new User(25789));
        list.add(new User(267));
        list.add(new User(67672));
        list.add(new User(69692));
        list.add(new User(2066));
        list.add(new User(26));

        Stream<User> stream = list.stream();
        stream.skip(2).limit(30).sorted().filter(u -> u.age > 100).distinct().map(u -> {
            return new User(u.age + 1);
        }).forEach(System.out::println);

        stream = list.stream();
        stream.skip(2).limit(30).sorted().filter(u -> u.age > 100).distinct().map(u -> {
            return new User(u.age + 1);
        }).findFirst();

        stream = list.stream();
        stream.skip(2).limit(30).sorted().filter(u -> u.age > 100).distinct().map(u -> {
            return new User(u.age + 1);
        }).collect(Collectors.averagingInt(u -> u.age));

        stream = list.stream();
        stream.skip(2).limit(30).sorted().filter(u -> u.age > 100).distinct().map(u -> {
            return new User(u.age + 1);
        }).min(Comparator.comparing(u -> u.age));

    }



    public static class User implements Comparable<User>{

        public User(int age) {
            super();
            this.age = age;
        }

        public int age;

        @Override
        public int compareTo(User o) {
            return this.age - o.age;
        }
    }

}
