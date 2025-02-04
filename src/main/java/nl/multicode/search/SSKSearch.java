package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.SSK;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SSKSearch finds similar sentences using the String Subsequence Kernel (SSK).
 */
@ApplicationScoped
public class SSKSearch implements Search {
    private final SSK ssk;

    public SSKSearch(SSK ssk) {
        this.ssk = ssk;
    }

    @Override
    public String getAlgorithmName() {
        return "SSK";
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> ssk.sim(searchTerm, sentence) >= computeThreshold(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, ssk.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= 0.2) // Filter low-confidence matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .collect(Collectors.toList());
    }

    private double computeThreshold(String searchTerm) {
        return Math.max(0.2, 1.0 - (searchTerm.length() * 0.02)); // Adaptive thresholding
    }
}
