package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Editex;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EditexSearch finds similar sentences based on phonetic edit distance.
 */
@ApplicationScoped
public class EditexSearch implements Search {

    private final Editex editex;

    public EditexSearch(Editex editex) {
        this.editex = editex;
    }

    @Override
    public String getAlgorithmName() {
        return "Editex";
    }

    /**
     * Finds sentences in the given list that have a similarity greater than or equal to the adaptive threshold.
     *
     * @param searchTerm The search term.
     * @param sentences  The list of sentences to search through.
     * @return A list of similar sentences.
     */
    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, editex.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= computeThreshold(searchTerm)) // Adaptive threshold
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest similarity
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Returns ranked matches sorted by similarity.
     *
     * @param searchTerm The search term.
     * @param sentences  The list of sentences to compare.
     * @return Ranked list of sentence matches with similarity scores.
     */
    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, editex.sim(searchTerm, sentence)))
                .filter(entry -> entry.getValue() >= computeThreshold(searchTerm)) // Filter low-confidence matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest score
                .collect(Collectors.toList());
    }

    /**
     * Computes an adaptive similarity threshold.
     * - Long search terms require a **lower** threshold (higher precision).
     * - Short search terms have a **higher** threshold (allows more matches).
     *
     * @param searchTerm The input search term.
     * @return The computed threshold value.
     */
    private double computeThreshold(String searchTerm) {
        int length = searchTerm.length();
        if (length >= 15) {
            return 0.5; // Stricter match for long queries
        } else if (length >= 8) {
            return 0.6;
        } else {
            return 0.7; // Looser match for short queries
        }
    }
}
