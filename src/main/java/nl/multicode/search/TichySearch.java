package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Tichy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TichySimilaritySearch uses Tichy’s edit distance to find similar sentences.
 */
@ApplicationScoped
public class TichySearch implements Search {
    private final Tichy tichy;

    /**
     * Constructs a TichySimilaritySearch instance with default cost parameters.
     */
    public TichySearch() {
        this.tichy = new Tichy();
    }

    /**
     * Finds sentences in the list that are similar to the search sentence
     * based on the Tichy distance.
     *
     * @param searchSentence the sentence to compare against
     * @param sentences      the list of sentences to search through
     * @param threshold      the maximum allowed absolute Tichy distance for a match
     * @return a list of sentences that are similar to the search sentence
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> isSentenceSimilar(searchSentence, sentence, threshold))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether a sentence is similar to the search sentence by comparing their Tichy distance.
     *
     * @param searchSentence the sentence to compare against
     * @param sentence       the sentence to check
     * @param threshold      the maximum allowed Tichy distance
     * @return true if the distance is within the threshold; false otherwise
     */
    private boolean isSentenceSimilar(String searchSentence, String sentence, double threshold) {
        double distance = tichy.distAbs(searchSentence, sentence);
        return distance <= threshold;
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 2);
    }
}
