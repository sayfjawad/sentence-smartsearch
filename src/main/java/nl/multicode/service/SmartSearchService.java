package nl.multicode.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;
import nl.multicode.search.Search;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@ApplicationScoped
public class SmartSearchService {

    private final Instance<Search> searchAlgorithms;

    public SmartSearchService(Instance<Search> searchAlgorithms) {
        this.searchAlgorithms = searchAlgorithms;
    }

    public Map<String, List<Map.Entry<String, Double>>> performSearch(final String searchTerm, final String largeText) {
        int searchTermWordCount = searchTerm.split("\\s+").length;
        List<String> tokens = tokenizeText(largeText, searchTermWordCount);

        return searchAlgorithms.stream()
                .collect(Collectors.toMap(
                        Search::getAlgorithmName,
                        algorithm -> algorithm.searchWithScores(searchTerm, tokens).stream()
                                .filter(entry -> entry.getValue() >= getMinimumThreshold(algorithm.getAlgorithmName()))
                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                .limit(2) // Return top 5 matches per algorithm
                                .collect(Collectors.toList())
                ));
    }



    private double getMinimumThreshold(String algorithm) {
        return switch (algorithm) {
            case "RefinedSoundex", "DoubleMetaphone", "TypoDistance" -> 0.2; // More phonetic tolerance
            case "LevenshteinDistance", "Editex", "RatcliffObershelp" -> 0.6; // Medium strict
            default -> 0.5; // default strictness
        };
    }

    private List<String> tokenizeText(String text, int searchTermWordCount) {
        List<String> words = Arrays.asList(text.toLowerCase().split("\\s+"));
        List<String> ngrams = new ArrayList<>(words);

        int maxNgramSize = Math.min(words.size(), searchTermWordCount * 2); // Adaptive n-grams

        // Generate n-grams dynamically based on search term length
        for (int n = 2; n <= maxNgramSize; n++) { // Start from bigrams (n=2)
            final int finalN = n;
            IntStream.range(0, words.size() - finalN + 1)
                    .mapToObj(i -> String.join(" ", words.subList(i, i + finalN)))
                    .forEach(ngrams::add);
        }

        log.info("Generated n-grams: " + ngrams);
        return ngrams;
    }

}
