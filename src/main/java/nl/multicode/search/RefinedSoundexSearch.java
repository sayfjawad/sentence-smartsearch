package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.RefinedSoundex;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Searches for similar sentences using Refined Soundex.
 */
@ApplicationScoped
public class RefinedSoundexSearch implements Search {

    private final RefinedSoundex soundex;

    public RefinedSoundexSearch(RefinedSoundex soundex) {
        this.soundex = soundex;
    }

    @Override
    public String getAlgorithmName() {
        return "RefinedSoundex";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> getSimilarityScore(soundex.encode(searchTerm), soundex.encode(sentence)) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        String searchCode = soundex.encode(searchTerm);
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, getSimilarityScore(searchCode, soundex.encode(sentence))))
                .filter(entry -> entry.getValue() >= 0.4) // Filter out weak matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.4, 1.0 - (searchTerm.length() * 0.02)); // Adaptive threshold
    }

    private double getSimilarityScore(String code1, String code2) {
        int minLength = Math.min(code1.length(), code2.length());
        int common = 0;
        for (int i = 0; i < minLength; i++) {
            if (code1.charAt(i) == code2.charAt(i)) {
                common++;
            }
        }
        return (double) common / Math.max(code1.length(), code2.length());
    }
}
