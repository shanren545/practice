package shanren.alg;

import java.util.Arrays;

/***
 * leetcode 72题
 *
 * @author tanxianwen 2019年12月2日
 */
public class StringEditDistance {
    public int minDistance(String word1, String word2) {
        // todo args check
        int len1 = word1.length();
        int len2 = word2.length();
        int dp[][] = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            Arrays.fill(dp[i], 0);
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; i <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[len1][len2];
    }

    private int min(int i, int j, int k) {
        return Math.min(i, Math.min(j, k));
    }

}
