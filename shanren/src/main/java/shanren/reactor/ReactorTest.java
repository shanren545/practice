package shanren.reactor;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.common.base.Functions;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

public class ReactorTest {

	public static void main(String[] args) throws InterruptedException {
		Hooks.onOperatorDebug();
		Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

		List<String> iterable = Arrays.asList("foo", "bar", "foobar");
		Flux<String> seq2 = Flux.fromIterable(iterable);
		System.out.println(seq2.blockLast());

		Mono<String> noData = Mono.empty();

		Mono<String> data = Mono.just("foo");

		Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
		numbersFromFiveToSeven.subscribe(System.out::println);

		Flux<Integer> ints = Flux.range(1, 4).map(i -> {
			if (i <= 3)
				return i;
			throw new RuntimeException("Got to 4");
		});
		ints.subscribe(i -> System.out.println(i), error -> System.err.println("Error: " + error));

		Disposable d = Flux.range(1, 4).log().doOnNext(System.out::println).map(item -> item * 2)
				.map(Functions.identity()).subscribe(System.out::println);
		d.dispose();

//		Flux<Integer> ss = Flux.generate(stateSupplier, generator, stateConsumer);

		Flux.range(1, 6).map(i -> 10 / (i - 3)).onErrorReturn(0) // 1
				.map(i -> i * i).subscribe(System.out::println, System.err::println);

		Flux.range(1, 6).map(i -> 10 / (i - 3)).onErrorResume(e -> Mono.just(new Random().nextInt(6))) // 提供新的数据流
				.map(i -> i * i).subscribe(System.out::println, System.err::println);

		Flux.range(1, 4).log().subscribe();

		Flux<Integer> intss = Flux.range(1, 4).map(i -> {
			if (i <= 3)
				return i;
			throw new RuntimeException("Got to 4");
		});
		intss.subscribe(i -> System.out.println(i), error -> System.err.println("Error: " + error));

		ints = Flux.range(1, 4);
		ints.subscribe(i -> System.out.println(i), error -> System.err.println("Error " + error), () -> {
			System.out.println("Done");
		});

		Flux.interval(Duration.ofMillis(250)).map(input -> {
			if (input < 3)
				return "tick " + input;
			throw new RuntimeException("boom");
		}).elapsed().retry(1).subscribe(System.out::println, System.err::println);

		Thread.sleep(2100);
	}

}
