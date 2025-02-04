package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Overlap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OverlapSearch gebruikt de Overlap-coëfficiënt om vergelijkbare zinnen te vinden.
 */
@ApplicationScoped
public class OverlapSearch implements Search {

    private final Overlap overlap;

    public OverlapSearch(Overlap overlap) {
        this.overlap = overlap;
    }

    @Override
    public String getAlgorithmName() {
        return "Overlap";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> overlap.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, overlap.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.5) // Filter out weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by confidence
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.5, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }
}
