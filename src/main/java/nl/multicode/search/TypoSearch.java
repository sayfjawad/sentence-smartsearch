package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Typo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TypoSearch uses typo-aware edit distance to find similar sentences.
 */
@ApplicationScoped
public class TypoSearch implements Search {
    private final Typo typo;

    public TypoSearch(Typo typo) {
        this.typo = typo;
    }

    @Override
    public String getAlgorithmName() {
        return "Typo";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> typo.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, computeScore(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.4) // Filter out weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.4, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }

    private double computeScore(String searchTerm, String sentence) {
        return typo.sim(searchTerm, sentence); // Direct similarity score
    }
}
