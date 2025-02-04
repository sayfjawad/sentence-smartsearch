package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.LevenshteinDistance;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Searches for similar sentences based on Levenshtein distance.
 */
@ApplicationScoped
public class LevenshteinDistanceSearch implements Search {

    private final LevenshteinDistance levenshteinDistance;

    public LevenshteinDistanceSearch(LevenshteinDistance levenshteinDistance) {
        this.levenshteinDistance = levenshteinDistance;
    }

    @Override
    public String getAlgorithmName() {
        return "LevenshteinDistance";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> levenshteinDistance.dist(searchTerm, sentence) <= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, computeScore(searchTerm, sentence)))
                .collect(Collectors.toList());
    }

    private int computeThreshold(String searchTerm) {
        return Math.max(1, searchTerm.length() / 5); // 20% of search term length
    }

    private double computeScore(String searchTerm, String sentence) {
        int distance = levenshteinDistance.dist(searchTerm, sentence);
        return 1.0 - ((double) distance / Math.max(searchTerm.length(), sentence.length())); // Normalize score
    }
}
