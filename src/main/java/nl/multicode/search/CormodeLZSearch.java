package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.CormodeLZ;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CormodeLZSearch uses Cormode's LZ distance for sentence similarity detection.
 */
@ApplicationScoped
public class CormodeLZSearch implements Search {

    private final CormodeLZ cormodeLZ;

    public CormodeLZSearch(CormodeLZ cormodeLZ) {
        this.cormodeLZ = cormodeLZ;
    }

    @Override
    public String getAlgorithmName() {
        return "CormodeLZ";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> cormodeLZ.computeAbsoluteDistance(searchTerm, sentence) <= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, cormodeLZ.computeNormalizedDistance(searchTerm, sentence)))
                .sorted(Map.Entry.comparingByValue()) // Sort best matches (lower distance is better)
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(2, searchTerm.length() * 0.3); // Adaptive threshold
    }
}
