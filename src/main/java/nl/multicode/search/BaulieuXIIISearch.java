package nl.multicode.search;

import jakarta.enterprise.context.ApplicationScoped;
import nl.multicode.match.BaulieuXIII;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zoekt vergelijkbare zinnen op basis van de Baulieu XIII Distance.
 */
@ApplicationScoped
public class BaulieuXIIISearch implements Search {

    private final BaulieuXIII baulieuXIII;

    /**
     * Constructor met een externe BaulieuXIIIDistance-instantie (voor testbaarheid).
     */
    public BaulieuXIIISearch(BaulieuXIII baulieuXIII) {
        this.baulieuXIII = baulieuXIII;
    }

    /**
     * Vindt zinnen in de lijst die vergelijkbaar zijn met de zoekzin.
     *
     * @param searchSentence De zin waarmee wordt vergeleken.
     * @param sentences      De lijst met zinnen om door te zoeken.
     * @param threshold      De maximale toegestane Baulieu XIII Distance voor een match.
     * @return Een lijst met vergelijkbare zinnen.
     */
    public List<String> findSimilarSentences(final String searchSentence,
                                             final List<String> sentences,
                                             final double threshold) {
        return sentences.stream()
                .filter(sentence -> baulieuXIII.compute(searchSentence, sentence) <= threshold)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String searchTerm, List<String> sentences) {
        return findSimilarSentences(searchTerm, sentences, 0.5);
    }
}
