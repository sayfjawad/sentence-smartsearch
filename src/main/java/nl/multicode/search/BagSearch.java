package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Bag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Searches for similar sentences based on the Bag Distance algorithm.
 */
@ApplicationScoped
public class BagSearch implements Search {

    private final Bag bag;

    public BagSearch(Bag bag) {
        this.bag = bag;
    }

    @Override
    public String getAlgorithmName() {
        return "BagDistance";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> bag.compute(searchTerm, sentence) <= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, computeScore(searchTerm, sentence)))
                .collect(Collectors.toList());
    }

    private int computeThreshold(String searchTerm) {
        return Math.max(1, searchTerm.length() / 4); // 25% of term length
    }

    private double computeScore(String searchTerm, String sentence) {
        int distance = bag.compute(searchTerm, sentence);
        return 1.0 - ((double) distance / Math.max(searchTerm.length(), sentence.length())); // Normalize
    }
}
