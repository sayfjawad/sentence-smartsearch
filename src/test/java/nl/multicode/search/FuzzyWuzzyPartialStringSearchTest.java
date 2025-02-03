package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FuzzyWuzzyPartialStringSearchTest {


    @Test
    void testAlbertHeijnSimilaritySearch() {
        FuzzyWuzzyPartialStringSearch search = new FuzzyWuzzyPartialStringSearch();

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
        double threshold = 0.6;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // Expect that "Albört H ijn" and "Albert Heijn store" are considered similar.
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Albert Heijn store")
                .hasSize(2);

        similarSentences = search.search(searchSentence, sentences);
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Albert Heijn store")
                .hasSize(2);
    }
}
