package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the Bag Distance for string similarity comparison.
 */
@ApplicationScoped
public class Bag {

    /**
     * Computes the Bag Distance between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The Bag Distance between the two strings.
     */
    public int compute(String src, String tar) {
        if (tar.equalsIgnoreCase(src)) {
            return 0;
        } else if (src.isEmpty()) {
            return tar.length();
        } else if (tar.isEmpty()) {
            return src.length();
        }

        Map<String, Integer> srcBag = createWordBag(src);
        Map<String, Integer> tarBag = createWordBag(tar);

        int srcOnlyCard = multisetDifferenceCardinality(srcBag, tarBag);
        int tarOnlyCard = multisetDifferenceCardinality(tarBag, srcBag);

        return Math.max(srcOnlyCard, tarOnlyCard);
    }

    /**
     * Creates a word-based multiset (bag) from a string.
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
     * Computes the cardinality of the difference between two multisets.
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
