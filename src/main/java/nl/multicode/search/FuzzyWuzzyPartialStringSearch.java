package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.FuzzyWuzzyPartialString;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyPartialStringDistanceSearch finds similar sentences based on fuzzy matching.
 */
@ApplicationScoped
public class FuzzyWuzzyPartialStringSearch implements Search {
    private final FuzzyWuzzyPartialString fuzzyDistance;

    /**
     * Constructs a FuzzyWuzzyPartialStringDistanceSearch instance.
     */
    public FuzzyWuzzyPartialStringSearch() {
        this.fuzzyDistance = new FuzzyWuzzyPartialString();
    }

    /**
     * Finds sentences in the given list that have a similarity greater than or equal to the threshold.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentences      The list of sentences to search through.
     * @param threshold      The minimum similarity score required (in the range [0,1]).
     * @return A list of similar sentences.
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> fuzzyDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 0.6);
    }
}
