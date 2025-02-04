package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * LIG3 implements a similarity measure based on Levenshtein and exact character matches.
 */
@ApplicationScoped
public class LIG3 {

    @Inject
    LevenshteinDistance levenshteinDistance;

    /**
     * Computes the LIG3 similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }

        int exactMatches = countExactMatches(src, tar);
        int cost = levenshteinDistance.dist(src, tar);
        int numerator = 2 * exactMatches;
        int denominator = numerator + cost;

        return denominator == 0 ? 0.0 : (double) numerator / denominator;
    }

    /**
     * Counts exact character matches at the same positions.
     *
     * @param src the source string
     * @param tar the target string
     * @return the count of exact character matches
     */
    private int countExactMatches(String src, String tar) {
        int minLength = Math.min(src.length(), tar.length());
        int matches = 0;
        for (int i = 0; i < minLength; i++) {
            if (src.charAt(i) == tar.charAt(i)) {
                matches++;
            }
        }
        return matches;
    }
}
