package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.RatcliffObershelp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RatcliffObershelpSearch gebruikt de Ratcliff-Obershelp gelijkenis om vergelijkbare zinnen te vinden.
 */
@ApplicationScoped
public class RatcliffObershelpSearch implements Search {

    private final RatcliffObershelp ratcliffObershelp;

    public RatcliffObershelpSearch(RatcliffObershelp ratcliffObershelp) {
        this.ratcliffObershelp = ratcliffObershelp;
    }

    @Override
    public String getAlgorithmName() {
        return "RatcliffObershelp";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> ratcliffObershelp.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, ratcliffObershelp.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.5) // Filter out weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.5, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }
}
