package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.IterativeSubString;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * IterativeSubStringSearch uses I-Sub similarity to find and rank similar sentences.
 */
@ApplicationScoped
public class IterativeSubStringSearch implements Search {

    private final IterativeSubString iterativeSubString;

    public IterativeSubStringSearch(IterativeSubString iterativeSubString) {
        this.iterativeSubString = iterativeSubString;
    }

    @Override
    public String getAlgorithmName() {
        return "IterativeSubString";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> iterativeSubString.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, iterativeSubString.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.5) // Ignore weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.4, 1.0 - (searchTerm.length() * 0.01)); // Adaptive threshold
    }
}
