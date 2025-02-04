package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Clement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Searches for similar sentences based on the Clement similarity.
 */
@ApplicationScoped
public class ClementSearch implements Search {

    private final Clement clement;

    public ClementSearch(Clement clement) {
        this.clement = clement;
    }

    @Override
    public String getAlgorithmName() {
        return "Clement";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> clement.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, clement.sim(searchTerm, sentence)))
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort best matches first
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.6, 1.0 - (searchTerm.length() * 0.02)); // Dynamic threshold
    }
}
