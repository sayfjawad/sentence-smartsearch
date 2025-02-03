package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.Overlap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OverlapDistanceSearch gebruikt de Overlap-coëfficiënt om vergelijkbare zinnen te vinden.
 */
@ApplicationScoped
public class OverlapSearch implements Search {
    private final Overlap overlap;

    /**
     * Construeert een OverlapDistanceSearch-instantie met standaardparameters.
     */
    public OverlapSearch() {
        this.overlap = new Overlap();
    }

    /**
     * Vindt zinnen in de lijst die een gelijkenisscore hebben die groter is dan of gelijk is aan de drempel.
     *
     * @param searchSentence de zin om te vergelijken
     * @param sentences      de lijst met zinnen om door te zoeken
     * @param threshold      de minimale gelijkenisscore vereist (in het bereik [0,1])
     * @return een lijst met vergelijkbare zinnen
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> overlap.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 0.7);
    }
}
