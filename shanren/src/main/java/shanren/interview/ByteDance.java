package shanren.interview;

public class ByteDance {
    public static void main(String[] args) {
        int[] prices = {3, 16, 1, 9, 4, 2};

        int min = Integer.MAX_VALUE;
        int maxVal = 0;
        for (int price : prices) {
            if (price < min) {
                min = price;
            }

            int val = price - min;
            if (val > maxVal) {
                maxVal = val;
            }
        }

        System.out.println(maxVal);

    }
}
