package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements Dice's Asymmetric I similarity measure.
 *
 * <p>
 * The similarity score is calculated as:
 * <pre>
 *     sim_DiceAsymmetricI(X, Y) = |X ∩ Y| / |X|
 * </pre>
 * where X and Y are the tokenized sets of character bigrams.
 * </p>
 */
@ApplicationScoped
public class DiceAsymmetricI {

    /**
     * Computes the Dice Asymmetric I similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double computeSimilarity(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }
        if (src.isEmpty() || tar.isEmpty()) {
            return 0.0;
        }

        Set<String> srcSet = generateBigrams(src);
        Set<String> tarSet = generateBigrams(tar);

        int intersection = computeIntersectionSize(srcSet, tarSet);
        int srcSize = srcSet.size();

        return (srcSize == 0) ? 0.0 : (double) intersection / srcSize;
    }

    /**
     * Generates bigrams from a string.
     *
     * @param str the input string
     * @return a set of bigrams
     */
    private Set<String> generateBigrams(String str) {
        Set<String> bigrams = new HashSet<>();
        str = str.toLowerCase().replaceAll("[^a-z]", ""); // Normalize input

        for (int i = 0; i < str.length() - 1; i++) {
            bigrams.add(str.substring(i, i + 2));
        }
        return bigrams;
    }

    /**
     * Computes the intersection size of two sets.
     *
     * @param set1 the first set
     * @param set2 the second set
     * @return the number of common elements
     */
    private int computeIntersectionSize(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection.size();
    }
}
