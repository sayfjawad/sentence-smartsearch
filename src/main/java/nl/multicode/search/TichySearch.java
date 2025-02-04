package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Tichy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TichySearch uses Tichy’s edit distance to find similar sentences.
 */
@ApplicationScoped
public class TichySearch implements Search {
    private final Tichy tichy;

    public TichySearch(Tichy tichy) {
        this.tichy = tichy;
    }

    @Override
    public String getAlgorithmName() {
        return "Tichy";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> tichy.distAbs(searchTerm, sentence) <= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, computeScore(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.3) // Filter weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private int computeThreshold(String searchTerm) {
        return Math.max(2, searchTerm.length() / 5); // Adaptive threshold
    }

    private double computeScore(String searchTerm, String sentence) {
        double distance = tichy.dist(searchTerm, sentence);
        return 1.0 - distance; // Normalize similarity score
    }
}
