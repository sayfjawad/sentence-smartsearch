package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.DoubleMetaphoneEncoder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Searches for similar sentences using Double Metaphone.
 */
@ApplicationScoped
public class DoubleMetaphoneSearch implements Search {

    private final DoubleMetaphoneEncoder encoder;

    public DoubleMetaphoneSearch(DoubleMetaphoneEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String getAlgorithmName() {
        return "DoubleMetaphone";
    }

    /**
     * Finds sentences in the list with a matching Double Metaphone encoding.
     *
     * @param searchTerm The input term.
     * @param sentences  The list of sentences to search in.
     * @return The list of matching sentences.
     */
    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .filter(sentence -> encoder.isPhoneticMatch(searchTerm, sentence))
                .collect(Collectors.toList());
    }

    /**
     * Finds sentences with phonetic similarity and scores them based on confidence.
     *
     * @param searchTerm The input term.
     * @param sentences  The list of sentences to search in.
     * @return A ranked list of matching sentences with confidence scores.
     */
    @Override
    public List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences) {
        return sentences.stream()
                .map(sentence -> Map.entry(sentence, computePhoneticScore(searchTerm, sentence)))
                .filter(entry -> entry.getValue() > 0.5) // Only include relevant matches
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort by highest confidence
                .collect(Collectors.toList());
    }

    /**
     * Computes a phonetic similarity score between two sentences.
     * - 1.0 if primary and alternate encodings match.
     * - 0.8 if only the primary encodings match.
     * - 0.6 if only the alternate encodings match.
     * - 0.0 otherwise.
     */
    private double computePhoneticScore(String searchTerm, String sentence) {
        boolean primaryMatch = encoder.encode(searchTerm).equals(encoder.encode(sentence));
        boolean alternateMatch = encoder.encodeAlternate(searchTerm).equals(encoder.encodeAlternate(sentence));

        if (primaryMatch && alternateMatch) {
            return 1.0;
        } else if (primaryMatch) {
            return 0.8;
        } else if (alternateMatch) {
            return 0.6;
        }
        return 0.0;
    }
}
