package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EditexSearchTest {

    @Test
    void testAlbertHeijnSimilaritySearch() {
        EditexSearch search = new EditexSearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        // Choose a threshold based on expected similarity scores.
        double threshold = 0.7;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // Expect that "Albört H ijn" and "Alber Heijn" are considered similar.
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Alber Heijn")
                .hasSize(2);

        similarSentences = search.search(searchSentence, sentences);
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Alber Heijn")
                .hasSize(2);
    }
}
