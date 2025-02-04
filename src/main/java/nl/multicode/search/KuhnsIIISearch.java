package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.KuhnsIII;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * KuhnsIIISearch uses Kuhns III correlation to find and rank similar sentences.
 */
@ApplicationScoped
public class KuhnsIIISearch implements Search {

    private final KuhnsIII kuhnsDistance;

    public KuhnsIIISearch(KuhnsIII kuhnsDistance) {
        this.kuhnsDistance = kuhnsDistance;
    }

    @Override
    public String getAlgorithmName() {
        return "KuhnsIII";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> kuhnsDistance.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, kuhnsDistance.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.2) // Filter out weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by confidence
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.2, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }
}
