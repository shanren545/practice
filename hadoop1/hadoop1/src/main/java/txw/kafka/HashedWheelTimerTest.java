package txw.kafka;

import java.util.concurrent.TimeUnit;

import io.netty.util.HashedWheelTimer;

public class HashedWheelTimerTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        final TimeUnit u = TimeUnit.SECONDS;
        HashedWheelTimer timer = new HashedWheelTimer(1, u, 3);


        timer.start();
        for (int i = 0; i < 100100; i++) {
            timer.newTimeout(timeout -> {
                System.out.print("txw");
            }, i + 1, u);

        }


        timer.newTimeout(timeout -> {
            System.out.print("txw");
        }, 8, u);

        timer.newTimeout(timeout -> {
            System.out.print("txw");
        }, 11, u);

        timer.newTimeout(timeout -> {
            System.out.print("txw");
        }, 100, u);

        System.out.print("");

    }

}
