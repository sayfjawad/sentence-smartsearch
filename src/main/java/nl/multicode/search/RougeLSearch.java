package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.RougeL;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RougeLDistanceSearch uses Rouge-L similarity to find similar sentences.
 */
@ApplicationScoped
public class RougeLSearch implements Search {
    private final RougeL rougeL;

    /**
     * Constructs a RougeLDistanceSearch instance with the default beta (8).
     */
    public RougeLSearch() {
        this.rougeL = new RougeL();
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
                .filter(sentence -> rougeL.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 0.5);
    }
}
