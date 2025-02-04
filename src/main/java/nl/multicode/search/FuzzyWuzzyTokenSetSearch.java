package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.FuzzyWuzzyTokenSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyTokenSetSearch finds similar sentences based on tokenized fuzzy matching.
 */
@ApplicationScoped
public class FuzzyWuzzyTokenSetSearch implements Search {
    private final FuzzyWuzzyTokenSet fuzzyTokenSetDistance;

    public FuzzyWuzzyTokenSetSearch(FuzzyWuzzyTokenSet fuzzyTokenSetDistance) {
        this.fuzzyTokenSetDistance = fuzzyTokenSetDistance;
    }

    @Override
    public String getAlgorithmName() {
        return "FuzzyWuzzyTokenSet";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> fuzzyTokenSetDistance.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, fuzzyTokenSetDistance.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.4) // Ignore low-confidence matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest score
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.4, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }
}
