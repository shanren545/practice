package shanren.alg;

public class Pow {

    public static void main(String[] args) {
        long x = 3;
        int n = 12;
        System.out.println(Math.pow(x, n));
        System.out.println(pow(x, n));
        System.out.println(pow2(x, n));
        System.out.println(pow3(x, n));
        System.out.println(Integer.numberOfTrailingZeros(Integer.highestOneBit(10)));
    }

    // 算法错误
    public static long pow(long x, int n) {
        // todo

        long rt = x;
        int remain = 0;
        n = n / 2;
        remain += n % 2; // 每次把余数记下
        while (n > 0) {
            remain += n % 2; // 每次把余数记下
            rt = rt * rt;
            n = n / 2;
        }
        while (remain-- > 0) {
            rt *= x;
        }
        return rt;
    }

    // 算法正确
    public static long pow2(long x, int n) {
        // todo

        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }

        long rt = x;
        int count = Integer.numberOfTrailingZeros(Integer.highestOneBit(n));
        int begin = count;
        while (count-- > 0) {
            rt = rt * rt;
        }

        if (begin == 0) {
            return rt;
        }

        int mask = 1 << begin - 1;
        n = n & mask;
        return rt * pow2(x, n);
    }

    // 算法正确
    public static long pow3(long x, int n) {
        // todo
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }

        long rt = 1;
        while (n > 0) {
            if ((n & 1) == 1) {// 奇数
                rt *= x;
            }
            n = n >>> 1;
            // if (n != 0) {
            x *= x;
            // }
        }

        return rt;
    }


}
