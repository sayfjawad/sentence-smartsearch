package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.LIG3;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LIG3DistanceSearch finds similar sentences based on LIG3 similarity.
 */
@ApplicationScoped
public class LIG3Search implements Search {
    private final LIG3 lig3;

    /**
     * Constructs a LIG3DistanceSearch instance.
     */
    public LIG3Search() {
        this.lig3 = new LIG3();
    }

    /**
     * Finds sentences in the given list that have a similarity greater than or equal to the threshold.
     *
     * @param searchSentence the sentence to compare against
     * @param sentences      the list of sentences to search through
     * @param threshold      the minimum similarity score required (in the range [0,1])
     * @return a list of similar sentences
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> lig3.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 0.7);
    }
}
