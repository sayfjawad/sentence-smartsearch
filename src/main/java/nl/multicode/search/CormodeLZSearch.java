package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.CormodeLZ;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CormodeLZSimilaritySearch uses Cormode's LZ distance to find similar sentences.
 */
@ApplicationScoped
public class CormodeLZSearch implements Search {
    private final CormodeLZ cormodeLZ;

    /**
     * Constructs a CormodeLZSimilaritySearch instance with default settings.
     */
    public CormodeLZSearch() {
        this.cormodeLZ = new CormodeLZ();
    }

    /**
     * Finds sentences in the provided list that are similar to the search sentence,
     * based on Cormode's LZ distance.
     *
     * @param searchSentence the sentence to compare against
     * @param sentences      the list of sentences to search through
     * @param threshold      the maximum allowed absolute distance for a match
     * @return a list of sentences that are similar to the search sentence
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> isSentenceSimilar(searchSentence, sentence, threshold))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether the Cormode LZ distance between the search sentence and the given sentence
     * is within the specified threshold.
     *
     * @param searchSentence the sentence to compare against
     * @param sentence       the sentence to check
     * @param threshold      the maximum allowed distance
     * @return true if the sentence is considered similar; false otherwise
     */
    private boolean isSentenceSimilar(String searchSentence, String sentence, double threshold) {
        double distance = cormodeLZ.distAbs(searchSentence, sentence);
        return distance <= threshold;
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 3);
    }
}
