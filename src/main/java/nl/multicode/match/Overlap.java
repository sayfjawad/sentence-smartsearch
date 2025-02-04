package nl.multicode.match;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

/**
 * Overlap implementeert de Overlap-coëfficiënt.
 * <p>
 * De formule is gedefinieerd als:
 * <pre>
 *     sim_overlap(X, Y) = |X ∩ Y| / min(|X|, |Y|)
 * </pre>
 * waar X en Y de getokeniseerde sets van tekens van de invoerstrings zijn.
 */
@ApplicationScoped
public class Overlap {

    /**
     * Bereken de Overlap-coëfficiënt tussen twee strings.
     *
     * @param src de bronstring
     * @param tar de doelstring
     * @return de gelijkenisscore in het bereik [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }

        Set<Character> srcSet = tokenize(src);
        Set<Character> tarSet = tokenize(tar);

        int intersectionSize = computeIntersectionSize(srcSet, tarSet);
        int minSize = Math.min(srcSet.size(), tarSet.size());

        return (minSize == 0) ? 0.0 : (double) intersectionSize / minSize;
    }

    /**
     * Tokeniseer een string in een set unieke tekens.
     *
     * @param str de invoerstring
     * @return een set unieke tekens
     */
    private Set<Character> tokenize(String str) {
        Set<Character> tokens = new HashSet<>();
        for (char c : str.toCharArray()) {
            tokens.add(c);
        }
        return tokens;
    }

    /**
     * Bereken de grootte van de intersectie tussen twee sets.
     *
     * @param set1 de eerste set
     * @param set2 de tweede set
     * @return het aantal gemeenschappelijke elementen in de intersectie
     */
    private int computeIntersectionSize(Set<Character> set1, Set<Character> set2) {
        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection.size();
    }
}
