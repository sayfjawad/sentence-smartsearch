package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Indel;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * IndelSearch finds similar sentences based on insertion/deletion (Indel) distance.
 */
@ApplicationScoped
public class IndelSearch implements Search {

    private final Indel indelDistance;

    public IndelSearch(Indel indelDistance) {
        this.indelDistance = indelDistance;
    }

    @Override
    public String getAlgorithmName() {
        return "Indel";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> indelDistance.compute(searchTerm, sentence) <= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, computeScore(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.5) // Ignore weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private int computeThreshold(String searchTerm) {
        return Math.max(1, searchTerm.length() / 4); // Adaptive threshold (25% of term length)
    }

    private double computeScore(String searchTerm, String sentence) {
        int distance = indelDistance.compute(searchTerm, sentence);
        return 1.0 - ((double) distance / Math.max(searchTerm.length(), sentence.length())); // Normalize score
    }
}
