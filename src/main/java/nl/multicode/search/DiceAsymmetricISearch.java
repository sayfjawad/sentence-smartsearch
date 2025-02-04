package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.DiceAsymmetricI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DiceAsymmetricISearch uses Dice's Asymmetric I similarity to find similar sentences.
 */
@ApplicationScoped
public class DiceAsymmetricISearch implements Search {

    private final DiceAsymmetricI diceDistance;

    public DiceAsymmetricISearch(DiceAsymmetricI diceDistance) {
        this.diceDistance = diceDistance;
    }

    @Override
    public String getAlgorithmName() {
        return "DiceAsymmetricI";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> diceDistance.computeSimilarity(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, diceDistance.computeSimilarity(searchTerm, sentence)))
                .filter(entry -> entry.getValue() > 0.3) // Ignore low-confidence matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.3, 1.0 - (searchTerm.length() * 0.01)); // Adaptive threshold
    }
}
