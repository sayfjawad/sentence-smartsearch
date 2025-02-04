package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the String Subsequence Kernel (SSK) similarity measure.
 */
@ApplicationScoped
public class SSK {

    private static final double DEFAULT_LAMBDA = 0.9;
    private final double lambda;

    public SSK() {
        this.lambda = DEFAULT_LAMBDA;
    }

    public SSK(double lambda) {
        this.lambda = lambda;
    }

    /**
     * Computes the SSK similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }

        Map<String, Integer> srcTokens = tokenize(src);
        Map<String, Integer> tarTokens = tokenize(tar);

        double score = 0.0;
        for (String token : srcTokens.keySet()) {
            if (tarTokens.containsKey(token)) {
                score += srcTokens.get(token) * tarTokens.get(token);
            }
        }

        double srcNorm = computeNorm(srcTokens);
        double tarNorm = computeNorm(tarTokens);

        return (srcNorm == 0 || tarNorm == 0) ? 0.0 : score / (srcNorm * tarNorm);
    }

    /**
     * Tokenizes a string into subsequences.
     *
     * @param str the input string
     * @return a map of subsequence counts
     */
    private Map<String, Integer> tokenize(String str) {
        Map<String, Integer> tokens = new HashMap<>();
        int len = str.length();

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                String sub = str.substring(i, j);
                tokens.put(sub, tokens.getOrDefault(sub, 0) + 1);
            }
        }
        return tokens;
    }

    /**
     * Computes the norm value for SSK similarity calculation.
     *
     * @param tokenMap The map of token frequencies.
     * @return The computed norm value.
     */
    private double computeNorm(Map<String, Integer> tokenMap) {
        return Math.sqrt(tokenMap.values().stream().mapToDouble(v -> v * v).sum());
    }
}
