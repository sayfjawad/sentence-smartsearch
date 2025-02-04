package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.RougeL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RougeLSearch uses Rouge-L similarity to find similar sentences.
 */
@ApplicationScoped
public class RougeLSearch implements Search {

    private final RougeL rougeL;

    public RougeLSearch(RougeL rougeL) {
        this.rougeL = rougeL;
    }

    @Override
    public String getAlgorithmName() {
        return "RougeL";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> rougeL.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, rougeL.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.5) // Filter low-confidence matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest score
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.5, 1.0 - (searchTerm.length() * 0.02)); // Adaptive thresholding
    }
}
