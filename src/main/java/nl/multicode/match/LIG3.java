package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * LIG3Distance implements a similarity measure based on Levenshtein and exact character matches.
 */
@ApplicationScoped  // Make it a CDI bean
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

        int minLength = Math.min(src.length(), tar.length());
        int exactMatches = 0;

        for (int i = 0; i < minLength; i++) {
            if (src.charAt(i) == tar.charAt(i)) {
                exactMatches++;
            }
        }

        int cost = levenshteinDistance.dist(src, tar);
        int numerator = 2 * exactMatches;
        int denominator = numerator + cost;

        return denominator == 0 ? 0.0 : (double) numerator / denominator;
    }
}
