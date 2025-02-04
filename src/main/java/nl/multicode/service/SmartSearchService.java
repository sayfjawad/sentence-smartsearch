package nl.multicode.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import nl.multicode.search.Search;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class SmartSearchService {

    @Any
    Instance<Search> searchAlgorithms;

    public Map<String, List<String>> performSearch(final String searchTerm, final String largeText) {
        // Generate overlapping windows from large text
        List<String> windowSubstrings = generateSlidingWindows(largeText);

        // Perform search over all window substrings
        return searchAlgorithms.stream()
                .collect(Collectors.toMap(
                        Search::getAlgorithmName, // Algorithm name as key
                        algorithm -> algorithm.search(searchTerm, windowSubstrings) // Search in each window
                ));
    }

    private List<String> generateSlidingWindows(String text) {
        int textLength = text.length();

        // Define window size as 20% of the total text length (minimum 10, max capped)
        int windowSize = Math.max(10, textLength / 5);

        // Define step size as 50% of the window size (ensuring overlap)
        int stepSize = Math.max(5, windowSize / 2);

        return IntStream.iterate(0, i -> i + stepSize)
                .limit((textLength - windowSize) / stepSize + 1)
                .mapToObj(start -> text.substring(start, Math.min(start + windowSize, textLength)))
                .collect(Collectors.toList());
    }

}
