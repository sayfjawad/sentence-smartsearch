package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Editex implements a phonetic edit distance similarity measure.
 * Assigns lower costs for substitutions between characters in the same phonetic group.
 */
@ApplicationScoped
public class Editex {

    private static final int MATCH_COST = 0;
    private static final int SAME_GROUP_COST = 1;
    private static final int MISMATCH_COST = 2;

    private static final Map<Character, Integer> LETTER_GROUP_MAP = new HashMap<>();

    static {
        Set.of(
                Set.of('A', 'E', 'I', 'O', 'U', 'Y'),
                Set.of('B', 'P'),
                Set.of('C', 'K', 'Q'),
                Set.of('D', 'T'),
                Set.of('L', 'R'),
                Set.of('M', 'N'),
                Set.of('G', 'J'),
                Set.of('F', 'V'),
                Set.of('S', 'X', 'Z')
        ).forEach(group -> group.forEach(c -> LETTER_GROUP_MAP.put(c, LETTER_GROUP_MAP.size())));
    }

    /**
     * Computes the Editex similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }

        src = normalize(src);
        tar = normalize(tar);

        int srcLen = src.length();
        int tarLen = tar.length();
        int[][] dp = new int[srcLen + 1][tarLen + 1];

        for (int i = 0; i <= srcLen; i++) {
            for (int j = 0; j <= tarLen; j++) {
                if (i == 0) {
                    dp[i][j] = j * MISMATCH_COST;
                } else if (j == 0) {
                    dp[i][j] = i * MISMATCH_COST;
                } else {
                    int cost = editexCost(src.charAt(i - 1), tar.charAt(j - 1));
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + MISMATCH_COST, dp[i][j - 1] + MISMATCH_COST),
                            dp[i - 1][j - 1] + cost
                    );
                }
            }
        }

        int maxLen = Math.max(srcLen, tarLen) * MISMATCH_COST;
        return 1.0 - (double) dp[srcLen][tarLen] / maxLen;
    }

    /**
     * Determines the substitution cost based on phonetic groups.
     */
    private int editexCost(char c1, char c2) {
        if (c1 == c2) {
            return MATCH_COST;
        }
        return LETTER_GROUP_MAP.getOrDefault(c1, -1).equals(LETTER_GROUP_MAP.getOrDefault(c2, -2))
                ? SAME_GROUP_COST
                : MISMATCH_COST;
    }

    /**
     * Normalizes input strings (uppercase, remove punctuation).
     */
    private String normalize(String str) {
        return str.toUpperCase().replaceAll("[^A-Z]", "");
    }
}
