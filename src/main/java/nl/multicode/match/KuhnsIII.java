package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

/**
 * KuhnsIII implements Kuhns III correlation for character similarity.
 */
@ApplicationScoped
public class KuhnsIII {

    /**
     * Computes the Kuhns III correlation between two strings.
     *
     * @param src The source string
     * @param tar The target string
     * @return The correlation score
     */
    public double corr(String src, String tar) {
        Set<Character> srcSet = tokenize(src);
        Set<Character> tarSet = tokenize(tar);
        Set<Character> population = new HashSet<>(srcSet);
        population.addAll(tarSet); // N = total unique characters

        int a = intersectionSize(srcSet, tarSet);
        int b = srcSet.size() - a;
        int c = tarSet.size() - a;
        int n = population.size();

        double delta_ab = a - ((double) (a + b) * (a + c) / n);
        if (delta_ab == 0) {
            return 0.0;
        }

        double denominator = (1 - (double) a / (2 * a + b + c)) * (2 * a + b + c - ((double) (a + b) * (a + c) / n));
        return delta_ab / denominator;
    }

    /**
     * Computes the Kuhns III similarity score.
     *
     * @param src The source string
     * @param tar The target string
     * @return The similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        return (1.0 / 3.0 + corr(src, tar)) / (4.0 / 3.0);
    }

    /**
     * Tokenizes a string into a set of unique characters.
     *
     * @param str The input string
     * @return A set of unique characters
     */
    private Set<Character> tokenize(String str) {
        Set<Character> tokens = new HashSet<>();
        for (char c : str.toCharArray()) {
            tokens.add(c);
        }
        return tokens;
    }

    /**
     * Computes the intersection size of two sets.
     *
     * @param set1 The first set
     * @param set2 The second set
     * @return The number of elements in the intersection
     */
    private int intersectionSize(Set<Character> set1, Set<Character> set2) {
        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection.size();
    }
}
