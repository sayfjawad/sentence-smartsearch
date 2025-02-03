package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Editex;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EditexDistanceSearch finds similar sentences based on phonetic edit distance.
 */
@ApplicationScoped
public class EditexSearch implements Search {
    private final Editex editex;

    /**
     * Constructs an EditexDistanceSearch instance.
     */
    public EditexSearch() {
        this.editex = new Editex();
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
                .filter(sentence -> editex.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 0.7);
    }
}
