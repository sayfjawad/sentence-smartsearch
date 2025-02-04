package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.FuzzyWuzzyPartialString;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyPartialStringSearch finds similar sentences based on fuzzy substring matching.
 */
@ApplicationScoped
public class FuzzyWuzzyPartialStringSearch implements Search {

    private final FuzzyWuzzyPartialString fuzzyDistance;

    public FuzzyWuzzyPartialStringSearch(FuzzyWuzzyPartialString fuzzyDistance) {
        this.fuzzyDistance = fuzzyDistance;
    }

    @Override
    public String getAlgorithmName() {
        return "FuzzyWuzzyPartialString";
    }

    /**
     * Finds sentences that match the search term above a dynamic similarity threshold.
     *
     * @param searchTerm The search term.
     * @param sentences  The list of sentences.
     * @return A list of matching sentences.
     */
    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, fuzzyDistance.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= computeThreshold(searchTerm)) // Dynamic threshold
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by similarity
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Returns a ranked list of matching sentences with similarity scores.
     *
     * @param searchTerm The search term.
     * @param sentences  The list of sentences.
     * @return A list of matches with scores.
     */
    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, fuzzyDistance.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= computeThreshold(searchTerm))
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    /**
     * Computes an adaptive similarity threshold.
     * - Longer search terms require a lower threshold (higher precision).
     * - Shorter search terms have a higher threshold (allows more matches).
     *
     * @param searchTerm The input search term.
     * @return The dynamic threshold value.
     */
    private double computeThreshold(String searchTerm) {
        int length = searchTerm.length();
        if (length >= 15) {
            return 0.5; // Stricter match for long queries
        } else if (length >= 8) {
            return 0.6;
        } else {
            return 0.7; // Looser match for short queries
        }
    }
}
