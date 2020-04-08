package shanren.alg;

import java.util.Arrays;

public class ShuDu {

    public static void main(String[] args) {
        int[][] data = new int[9][9];
        for (int i = 0; i < 9; i++) {
            Arrays.fill(data[i], 0);
        }
        new ShuDu().fill(data);
        printData(data);
    }

    private static void printData(int[][] data) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(data[i][j]);
            }
            System.out.println();
        }
    }

    public boolean fill(int[][] data) {
        // todo check args
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (0 != data[i][j]) {
                    continue;
                }
                // IntStream.rangeClosed(1, 9).forEach(num-> );
                for (int num = 1; num <= 9; num++) {
                    if (!isValid(data, num, i, j)) {
                        continue;
                    }
                    data[i][j] = num;
                    if (fill(data)) {
                        return true;
                    } else {
                        data[i][j] = 0;
                    }
                }
                return false;
            }
        }
        return true;
    }


    public boolean isValid(int[][] data, int tryNum, int row, int col) {
        for (int colIndex = 0; colIndex < 9; colIndex++) {
            if (data[row][colIndex] == tryNum) {
                return false;
            }
        }
        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            if (data[rowIndex][col] == tryNum) {
                return false;
            }
        }
        int rowStart = row / 3 * 3;
        int rowEnd = rowStart + 3;
        int colStart = col / 3 * 3;
        int colEnd = colStart + 3;
        for (int i = rowStart; i < rowEnd; i++) {
            for (int j = colStart; j < colEnd; j++) {
                if (data[i][j] == tryNum) {
                    return false;
                }
            }
        }
        return true;
    }

}
