package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements the Clement similarity measure.
 * <p>
 * Clement similarity is computed as:
 * <pre>
 *     sim_Clement(X, Y) =
 *     (|X ∩ Y| / |X|) * (1 - |X| / |N|) +
 *     (|(N - X) - Y| / |N - X|) * (1 - |N - X| / |N|)
 * </pre>
 * where N is the set of all unique words in the comparison.
 */
@ApplicationScoped
public class Clement {

    /**
     * Computes the Clement similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }

        Set<String> srcSet = tokenize(src);
        Set<String> tarSet = tokenize(tar);
        Set<String> population = new HashSet<>(srcSet);
        population.addAll(tarSet); // N is the union of both sets

        int a = intersectionSize(srcSet, tarSet);
        int b = srcSet.size() - a;
        int c = tarSet.size() - a;
        int d = population.size() - (a + b + c);
        int n = population.size();

        double score = 0.0;
        if (a + b > 0) {
            score += (a / (double) (a + b)) * (1 - (a + b) / (double) n);
        }
        if (c + d > 0) {
            score += (d / (double) (c + d)) * (1 - (c + d) / (double) n);
        }

        return score;
    }

    /**
     * Tokenizes a string into a set of unique words.
     *
     * @param str the input string
     * @return a set of unique words
     */
    private Set<String> tokenize(String str) {
        Set<String> tokens = new HashSet<>();
        for (String word : str.toLowerCase().split("\\s+")) {
            tokens.add(word);
        }
        return tokens;
    }

    /**
     * Computes the intersection size of two sets.
     *
     * @param set1 the first set
     * @param set2 the second set
     * @return the number of elements in the intersection
     */
    private int intersectionSize(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection.size();
    }
}
