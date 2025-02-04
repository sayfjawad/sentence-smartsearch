package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.FuzzyWuzzyTokenSort;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyTokenSortSearch finds similar sentences based on token-sorted fuzzy matching.
 */
@ApplicationScoped
public class FuzzyWuzzyTokenSortSearch implements Search {

    private final FuzzyWuzzyTokenSort fuzzyTokenSortDistance;

    public FuzzyWuzzyTokenSortSearch(FuzzyWuzzyTokenSort fuzzyTokenSortDistance) {
        this.fuzzyTokenSortDistance = fuzzyTokenSortDistance;
    }

    @Override
    public String getAlgorithmName() {
        return "FuzzyWuzzyTokenSort";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> fuzzyTokenSortDistance.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, fuzzyTokenSortDistance.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.4) // Ignore low-confidence matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest score
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.4, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }
}
