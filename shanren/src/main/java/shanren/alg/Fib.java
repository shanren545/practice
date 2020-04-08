package shanren.alg;

public class Fib {

    // f(n)=f(n-1)+f(n-2) 的非递归算法
    public static void main(String[] args) {
        int n = 60;
        long a = 0;
        long b = 1;
        for (int i = 2; i <= n; i++) {
            long tmp = b;
            b = a + b;
            a = tmp;
            System.out.println(b);
        }
    }



}
