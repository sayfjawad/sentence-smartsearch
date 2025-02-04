package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * RougeL implements the Rouge-L similarity measure based on Longest Common Subsequence (LCS).
 */
@ApplicationScoped
public class RougeL {

    private static final double DEFAULT_BETA = 8.0;
    private final double beta;

    public RougeL() {
        this.beta = DEFAULT_BETA;
    }

    public RougeL(double beta) {
        this.beta = beta;
    }

    /**
     * Computes the Rouge-L similarity between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The similarity score in the range [0,1].
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }
        if (src.isEmpty() || tar.isEmpty()) {
            return 0.0;
        }

        int lcsLength = longestCommonSubsequence(src, tar);
        double rLcs = (double) lcsLength / src.length();
        double pLcs = (double) lcsLength / tar.length();
        double betaSq = beta * beta;

        return (rLcs > 0 && pLcs > 0) ? ((1 + betaSq) * rLcs * pLcs) / (rLcs + betaSq * pLcs) : 0.0;
    }

    /**
     * Computes the Longest Common Subsequence (LCS) length between two strings.
     *
     * @param src The first string.
     * @param tar The second string.
     * @return The length of the LCS.
     */
    private int longestCommonSubsequence(String src, String tar) {
        int m = src.length();
        int n = tar.length();
        int[][] dp = new int[2][n + 1];

        for (int i = 1; i <= m; i++) {
            int curr = i % 2;
            int prev = (i - 1) % 2;

            for (int j = 1; j <= n; j++) {
                if (src.charAt(i - 1) == tar.charAt(j - 1)) {
                    dp[curr][j] = dp[prev][j - 1] + 1;
                } else {
                    dp[curr][j] = Math.max(dp[prev][j], dp[curr][j - 1]);
                }
            }
        }
        return dp[m % 2][n];
    }
}
