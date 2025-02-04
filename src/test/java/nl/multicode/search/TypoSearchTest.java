package nl.multicode.search;

import nl.multicode.match.Typo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TypoSearchTest {

    @Test
    void testAlbertHeijnSimilaritySearch() {
        TypoSearch search = new TypoSearch(new Typo());

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        double threshold = 0.7;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);
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
