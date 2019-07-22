package shanren.stream;

import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntStreamTest {
    public static void main(String[] args) {
        final IntPredicate predicate = i -> i > 5;
        IntStream s = IntStream.range(1, 10).filter(predicate).filter(i -> i > 6);
        // stream每次都是返回一个新的
        // stream只能使用一次
        // stream是不可改的
        // s.filter(i -> i > 6);
        // s.filter(i -> i > 7);
        s.forEach(System.out::println);
        
        Stream.of("one", "two", "three", "four")
        .filter(e -> e.length() > 3)
        .peek(e -> System.out.println("Filtered value: " + e))
        .map(String::toUpperCase)
        .peek(e -> System.out.println("Mapped value: " + e))
        .collect(Collectors.toList());
    }

}
