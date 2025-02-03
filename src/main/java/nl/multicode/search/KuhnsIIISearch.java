package nl.multicode.search;

import nl.multicode.match.KuhnsIII;

import java.util.List;
import java.util.stream.Collectors;

/**
 * KuhnsIIIDistanceSearch uses Kuhns III correlation to find similar sentences.
 */
public class KuhnsIIISearch {
    private final KuhnsIII kuhnsDistance;

    /**
     * Constructs a KuhnsIIIDistanceSearch instance with default parameters.
     */
    public KuhnsIIISearch() {
        this.kuhnsDistance = new KuhnsIII();
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
                .filter(sentence -> kuhnsDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
