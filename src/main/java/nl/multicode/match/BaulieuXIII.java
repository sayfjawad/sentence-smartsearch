package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements the Baulieu XIII Distance for string similarity comparison.
 */
@ApplicationScoped
public class BaulieuXIII {

    /**
     * Computes the Baulieu XIII similarity between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The similarity score in range [0.0, 1.0].
     */
    public double compute(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0; // Perfect match
        }

        Map<String, Integer> srcBag = createWordBag(src);
        Map<String, Integer> tarBag = createWordBag(tar);

        int intersection = multisetIntersectionCardinality(srcBag, tarBag);
        int srcOnly = multisetDifferenceCardinality(srcBag, tarBag);
        int tarOnly = multisetDifferenceCardinality(tarBag, srcBag);

        double numerator = srcOnly + tarOnly;
        double denominator = intersection + srcOnly + tarOnly + (intersection * Math.pow(intersection - 4, 2));

        if (denominator == 0.0) {
            return 0.0; // Avoid division by zero
        }

        return 1.0 - (numerator / denominator); // Convert to similarity score
    }

    /**
     * Creates a word-level multiset (bag) from a string.
     *
     * @param str The input string.
     * @return A map representing the word multiset.
     */
    private Map<String, Integer> createWordBag(String str) {
        Map<String, Integer> multiset = new HashMap<>();
        for (String word : str.toLowerCase().split("\\s+")) {
            multiset.put(word, multiset.getOrDefault(word, 0) + 1);
        }
        return multiset;
    }

    /**
     * Computes the cardinality of the multiset intersection.
     *
     * @param multisetA The first multiset.
     * @param multisetB The second multiset.
     * @return The cardinality of the intersection.
     */
    private int multisetIntersectionCardinality(Map<String, Integer> multisetA,
                                                Map<String, Integer> multisetB) {
        int cardinality = 0;
        for (Map.Entry<String, Integer> entry : multisetA.entrySet()) {
            String key = entry.getKey();
            int countA = entry.getValue();
            int countB = multisetB.getOrDefault(key, 0);
            cardinality += Math.min(countA, countB);
        }
        return cardinality;
    }

    /**
     * Computes the cardinality of the multiset difference (A - B).
     *
     * @param multisetA The first multiset.
     * @param multisetB The second multiset.
     * @return The cardinality of the difference.
     */
    private int multisetDifferenceCardinality(Map<String, Integer> multisetA,
                                              Map<String, Integer> multisetB) {
        int cardinality = 0;
        for (Map.Entry<String, Integer> entry : multisetA.entrySet()) {
            String key = entry.getKey();
            int countA = entry.getValue();
            int countB = multisetB.getOrDefault(key, 0);
            cardinality += Math.max(0, countA - countB);
        }
        return cardinality;
    }
}
