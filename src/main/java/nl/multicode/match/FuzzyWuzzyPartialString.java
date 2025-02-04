package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements FuzzyWuzzy Partial String similarity.
 * It slides the smaller string over the larger one and finds the best possible match.
 */
@ApplicationScoped
public class FuzzyWuzzyPartialString {

    /**
     * Computes the similarity between two strings using character matches.
     *
     * @param src The source string (typically the shorter one).
     * @param tar The target string (typically the longer one).
     * @return The similarity score in the range [0,1].
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }
        if (src.isEmpty() || tar.isEmpty()) {
            return 0.0;
        }

        // Ensure src is always the smaller string
        if (src.length() > tar.length()) {
            return sim(tar, src);
        }

        final int srcLen = src.length();
        return IntStream.rangeClosed(0, tar.length() - srcLen)
                .mapToDouble(start -> computeSimilarity(src, tar.substring(start, start + srcLen)))
                .max()
                .orElse(0.0);
    }

    /**
     * Computes the similarity between two strings using character matches.
     *
     * @param s1 The first string.
     * @param s2 The second string (should be of the same length as s1).
     * @return The similarity score.
     */
    private double computeSimilarity(String s1, String s2) {
        long matches = IntStream.range(0, s1.length())
                .filter(i -> s1.charAt(i) == s2.charAt(i))
                .count();
        return (double) matches / s1.length();
    }
}
