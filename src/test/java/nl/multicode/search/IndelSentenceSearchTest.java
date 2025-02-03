package nl.multicode.search;

import nl.multicode.match.Indel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IndelSentenceSearchTest {

    /**
     * Tests the functionality of the `SimilarSentenceSearch` class to find sentences similar
     * to a given search sentence within a specified threshold using Levenshtein distance.
     * <p>
     * The test verifies that the method correctly identifies sentences that are close to
     * "Albert Heijn" from a list of sentences and ensures the output meets the expected results.
     * Specifically, the test checks that:
     * - Sentences similar to "Albert Heijn", such as "Albört H ijn" and "Alber Heijn", are included
     * in the result based on the threshold.
     * - Irrelevant sentences ("Action", "Blokker", etc.) are excluded from the result.
     * <p>
     * This test uses assertions to validate the correctness of the result.
     */
    @Test
    void testAlbertHein() {
        IndelSearch search = new IndelSearch(new Indel());

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        int threshold = 3;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        assertThat(similarSentences)
                .contains("Alber Heijn")
                .hasSize(1);

        similarSentences = search.search(searchSentence, sentences);
        assertThat(similarSentences)
                .contains("Alber Heijn")
                .hasSize(1);
    }

}