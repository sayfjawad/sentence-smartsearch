package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.IterativeSubString;

import java.util.List;
import java.util.stream.Collectors;

/**
 * IterativeSubStringSimilaritySearch uses I-Sub similarity to find similar sentences.
 * A sentence is considered similar if its similarity score is at least the provided threshold.
 */
@ApplicationScoped
public class IterativeSubStringSearch implements Search {

    private final IterativeSubString iterativeSubString;

    /**
     * Constructs an IterativeSubStringSimilaritySearch with default parameters.
     * (hamacher = 0.6 and normalization disabled)
     */
    public IterativeSubStringSearch() {
        this.iterativeSubString = new IterativeSubString();
    }

    /**
     * Constructs an IterativeSubStringSimilaritySearch with custom parameters.
     *
     * @param hamacher         the constant factor for the Hamacher product
     * @param normalizeStrings whether to normalize the input strings
     */
    public IterativeSubStringSearch(double hamacher, boolean normalizeStrings) {
        this.iterativeSubString = new IterativeSubString(hamacher, normalizeStrings);
    }

    /**
     * Finds sentences in the given list that have a similarity (as computed by I-Sub)
     * greater than or equal to the provided threshold.
     *
     * @param searchSentence the sentence to compare against
     * @param sentences      the list of sentences to search through
     * @param threshold      the minimum similarity score required (in the range [0,1])
     * @return a list of similar sentences
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> iterativeSubString.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 0.5);
    }
}
