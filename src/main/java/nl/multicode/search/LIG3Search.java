package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.multicode.match.LIG3;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LIG3Search finds similar sentences based on LIG3 similarity.
 */
@ApplicationScoped
public class LIG3Search implements Search {

    private final LIG3 lig3;

    @Inject
    public LIG3Search(LIG3 lig3) {
        this.lig3 = lig3;
    }

    @Override
    public String getAlgorithmName() {
        return "LIG3";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> lig3.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, lig3.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.5) // Filter out weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by confidence
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.5, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }
}
